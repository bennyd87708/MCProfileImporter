# MCProfileImporter
MCProfileImporter is a tool for importing custom modpacks directly into the Minecraft launcher.

---

## Usage

- Create custom "pack.zip" containing everything necessary from .minecraft folder - should include necessary versions, mods, libraries, config, etc.
- Replace "src\main\resources\modpack\pack.zip" with your own "pack.zip"
- Edit "src\main\resources\modpack\profile.json" as desired - these are the parameters for the profile in the Minecraft launcher - you can add your own parameters like jvm arguments for example
- Replace "src\main\resources\gui\logo.png" with your own square logo - should be at least 70x70px but larger will work
- Edit "src\main\resources\gui\text.txt" first line to change the text that appears in the launcher
- Edit "src\main\resources\gui\text.txt" second line to change the pack folder name
- Build with Maven - "mvn install"
- Finished jar executable is in the \bin folder

---

## Information

**WARNING - Right now, this project is a heap of spaghetti that barely manages to function, but here is everything that is in progress:**
- check if linux and macosx support works
- update gui
- async second window
- progress bar/live info
- backup .minecraft and launcher profiles
- clean up/optimize code and variables
- document code
- human proof a bit more
- combine into one class
- more text config
- fix starting minimized bug
- automatic close after finish
- fix double resources in github
- download zip from web to decrease jar size
- get rid of try/catches and ioexception