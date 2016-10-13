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

    for i, figcaption in enumerate(soup.find_all('figcaption')):
        # Wrap the figure and the caption in a <figure>
        figure = figcaption.find_previous_sibling(True)
        if figure.name == 'p':
            figure.name = 'figure'
        else:
            figure = figure.wrap(soup.new_tag('figure'))
        figure.append(figcaption)

        # Give it a count
        caption_count = soup.new_tag('strong')
        caption_count.string = 'Figure {}. '.format(i + 1)
        figcaption.insert(0, caption_count)

    for h in soup.select('h2, h3, h4'):
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
