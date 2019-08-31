# MCProfileImporter
MCProfileImporter is a tool for importing custom modpacks directly into the Minecraft launcher.

---

## Usage

- Create custom "pack.zip" containing everything necessary from .minecraft folder - should include necessary versions, mods, libraries, config, etc.
- Clone the repository
- Replace "src\main\resources\modpack\pack.zip" with your own "pack.zip"
- Edit "src\main\resources\modpack\profile.json" as desired - these are the parameters for the profile in the Minecraft launcher - you can add your own parameters like jvm arguments for example
- Replace "src\main\resources\gui\logo.png" with your own square logo - should be at least 70x70px but smaller will work
- Edit "src\main\resources\gui\text.txt" first line to change the text that appears in the launcher
- Edit "src\main\resources\gui\text.txt" second line to change the pack name
- Download dependency: apache's commons-io-2.6.jar
- Import the project into Eclipse and add commons-io-2.6 as an external jar library
- Compile as runnable jar file

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
- use dependencies properly
- fix double resources in github
- download zip from web to decrease jar size
