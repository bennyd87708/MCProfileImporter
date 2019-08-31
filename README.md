# MCProfileImporter
MCProfileImporter is a tool for importing custom modpacks directly into the Minecraft launcher.

---

## Usage

- Create custom ".minecraft" folder containing modpack - should include necessary versions, mods, libraries, config, etc.
- Rename ".minecraft" to something else - ex: ".pixelmon"
- Zip folder as "pack.zip" - file structure can be seen in example
- Clone the repository
- Replace "src\main\resources\modpack\pack.zip" with your own "pack.zip"
- Edit "src\main\resources\modpack\profile.json" as desired - these are the parameters for the profile in the Minecraft launcher
- Replace "src\main\resources\gui\logo.png" with your own square logo - should be at least 70x70px but smaller will work
- Edit "src\main\resources\gui\text.txt" to change the text that appears in the launcher
- Download dependencies: srikanth's zip4j-2.1.2 and apache's commons-io-2.6.jar
- Import the project into Eclipse and import zip4j-2.1.2 as a required project and add commons-io-2.6 as an external jar library
- Compile as runnable jar file

---

## Information

**WARNING - Right now, this project is a heap of spaghetti that barely manages to function, but here is everything that is planned:**
- check if linux and macosx support works
- allow custom .minecraft directory name
- update gui
- async second window
- progress bar/live info
- backup .minecraft and launcher profiles
- get rid of zip4j
- clean up/optimize code and variables
- document code
- extract straight to appdata
- human proof a bit more
- replace existing profile
- update via overwrite
- combine into one class
- more text config
- profile title to directory name
- use proper temp files
- fix starting minimized bug
- automatic close after finish
- use dependencies properly
- fix double resources in github