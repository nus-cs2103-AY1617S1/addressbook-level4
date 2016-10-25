@echo off

REM for cs2103t admin purposes

if not exist Collate-TUI.jar (
    echo You must put Collate-TUI.jar on the root directory.
) else (

    java -jar Collate-TUI.jar collate from src/main to collated/main include java, fxml, css
    java -jar Collate-TUI.jar collate from src/test to collated/test include java
    java -jar Collate-TUI.jar collate from docs to collated/docs include md, html
)