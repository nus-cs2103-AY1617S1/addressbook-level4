import markdown
import codecs

HEADER = """<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" type="text/css" href="styles.css">
  <link rel="stylesheet" type="text/css" href="normalize.css">
  <link rel="stylesheet" type="text/css" href="skeleton.css">
</head>
<body>
<div class="container">
"""
END = """
</div>
</body>
</html>
"""

extensions = [
	"markdown.extensions.extra",
	"markdown.extensions.codehilite",
	"markdown.extensions.toc",
	"markdown.extensions.sane_lists",
]
list_of_files = [
	"DeveloperGuide",
	"UserGuide",
]

md = markdown.Markdown(extensions=extensions)

for file_path in list_of_files:
	input_file = codecs.open("{}.md".format(file_path), mode="r", encoding="utf-8")
	text = input_file.read()
	content = md.reset().convert(text)
	output_file = codecs.open("{}.html".format(file_path), "w",
	                          encoding="utf-8",
	                          errors="xmlcharrefreplace",
	)
	output_file.write(HEADER)
	output_file.write(content)
	output_file.write(END)
