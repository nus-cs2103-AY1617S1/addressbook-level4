import markdown
from markdown.extensions.toc import TocExtension
from bs4 import BeautifulSoup
from os import path

files = {
    "DeveloperGuide": "Developer Guide",
    "UserGuide": "User Guide",
}

base = path.dirname(path.abspath(__file__))
output_dir = path.join(base, "../")

md = markdown.Markdown(extensions=[
    "markdown.extensions.extra",
    "markdown.extensions.codehilite",
    "markdown.extensions.admonition",
    TocExtension(permalink='#'),
    "markdown.extensions.sane_lists",
])

with open(path.join(base, "template.html"), encoding="utf-8") as f:
    template = f.read()

for filename, title in files.items():
    input_path = path.join(output_dir, filename + ".md")
    output_path = path.join(output_dir, filename + ".html")

    with open(input_path, encoding="utf-8") as input:
        source = input.read()

    html = md.reset().convert(source)
    soup = BeautifulSoup(html, 'html.parser')

    # Addition figure captions 
    for i, figcaption in enumerate(soup.find_all('figcaption')):
        # Wrap the figure and the caption in a <figure>
        figure = figcaption.find_previous_sibling(True)
        if not figure:
            print("Cannot find figure for " + str(figcaption))
            continue
        
        if figure.name == 'p':
            figure.name = 'figure'
        else:
            figure = figure.wrap(soup.new_tag('figure'))
        figure.append(figcaption)

        # Give it a count
        caption_count = soup.new_tag('strong')
        caption_count.string = 'Figure {}. '.format(i + 1)
        figcaption.insert(0, caption_count)

    # Adding numbering and no-page-break wrappers around headings 
    sections = [0, 0, 0]
    for h in soup.find_all(['h2', 'h3', 'h4']):
        # Number the heading 
        if h.name == 'h2':
            sections = [sections[0] + 1, 0, 0]
            number = '{} '.format(sections[0])
        elif h.name == 'h3':
            sections[1] += 1
            sections[2] = 0
            number = '{}.{} '.format(*sections[0:2])
        elif h.name == 'h4':
            sections[2] += 1
            number = '{}.{}.{} '.format(*sections)

        number_tag = soup.new_tag('span')
        number_tag.string = number
        h.insert(0, number_tag)

        # Wrap the tag 
        next_tag = h.find_next_sibling(True)
        if not next_tag:
            continue
        no_break = h.wrap(soup.new_tag('div'))
        no_break['class'] = 'no-page-break'
        no_break.append(next_tag)

    output_content = template.format(title=title, html=soup, classname=filename.lower())

    with open(output_path, mode="w", encoding="utf-8") as output:
        output.write(output_content)

    print("Converting {} to {}".format(input_path, output_path))
