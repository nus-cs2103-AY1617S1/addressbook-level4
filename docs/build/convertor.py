import markdown
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
    "markdown.extensions.toc",
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
	output_content = template.format(title, html)

	with open(output_path, mode="w", encoding="utf-8") as output:
		output.write(output_content)

	print("Converting {} to {}".format(input_path, output_path))
