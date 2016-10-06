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
listOfFiles = [
	"DeveloperGuide.md",
	"UserGuide.md",
]

input_file = codecs.open("DeveloperGuide.md", mode="r", encoding="utf-8")
text = input_file.read()
html = markdown.markdown(text, extensions=extensions)
output_file = codecs.open("DeveloperGuide.html", "w",
                          encoding="utf-8",
                          errors="xmlcharrefreplace",
)
output_file.write(HEADER)
output_file.write(html)
output_file.write(END)
