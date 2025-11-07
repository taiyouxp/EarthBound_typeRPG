# Earthbound(Mother)-like Java RPG Project

This project uses Maven and Flyway to manage the build and database. The database schema (tables, columns) is set up and upgraded automatically when you run the application.<br><br>
The main goal is to practice SQL and learn  about the Java ecossystem while doing something that i think is cool. A CRUD, tbh. 

## -> Setup Instructions

You must install **Java**, **Maven**, and **PostgreSQL** to run this project.

### Step 1: Install a Java JDK (v11 or higher)

If you don't have a Java Development Kit, you can download it from [Adoptium](https://adoptium.net/temurin/releases/), [OpenJDK](https://https://openjdk.org/install/) or use a package manager.

* **Windows (winget):** `winget install -e --id EclipseAdoptium.Temurin.17.JDK`
* **macOS (Homebrew):** `brew install eclipse-temurin`
* **Linux (Debian/Ubuntu):** `sudo apt install openjdk-17-jdk`
* **Linux (Arch):** `sudo pacman -S jdk17-openjdk`

### Step 2: Install Maven

This manages the project, downloads all libraries, and runs the code.

* **Windows (Recommended: winget)**
    ```bash
    winget install -e --id Apache.Maven
    ```

* **macOS (Recommended: Homebrew)**
    ```bash
    brew install maven
    ```

* **Linux (Debian/Ubuntu)**
    ```bash
    sudo apt update
    sudo apt install maven
    ```

* **Linux (Fedora)**
    ```bash
    sudo dnf install maven
    ```

* **🐧 Linux (Arch)**
    ```bash
    sudo pacman -S maven
    ```

* **Manual "Works Anywhere" Method** (If the commands above fail)
    1.  [Download the binary .zip file from the Apache Maven Website](https://maven.apache.org/download.cgi).
    2.  Unzip it to a permanent folder (like `C:\Program Files\Maven` or `/opt/maven`).
    3.  Add it to your system's `PATH` environment variable.
        * **On Windows:** Search for "Edit the system environment variables", click "Environment Variables...", and in "System variables", edit the `Path` variable. Add a new entry pointing to the `bin` folder (e.g., `C:\Program Files\Maven\apache-maven-3.9.8\bin`).
        * **On Linux/macOS:** Add these lines to your `~/.bashrc` or `~/.zshrc`:
            ```bash
            export M2_HOME=/path/to/your/maven/folder
            export PATH=$M2_HOME/bin:$PATH
            ```

**Verify your Java and Maven install by opening a *new* terminal and running:**
```bash
java -version
mvn -version
```

### Step 3: Install PostgreSQL (v12 or higher)
This is the database.
* Windows (Recommended: EDB Installer)

1. Go to the PostgreSQL Windows download page.

2. Download the interactive installer for your version (e.g., "PostgreSQL 16").

3. Run the installer. During installation, you will be asked to set a password for the `postgres` superuser. You have to remember this password.

4. The installer will also install `psql`, a command-line tool you'll need.  

* macOS (homebrew):

```bash
brew install postgresql@17.6 
brew services start postgresql@17.6
```

* Linux (Debian/Ubuntu based)

```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql.service
```

* Linux (Arch based) 
```bash
sudo pacman -S postgresql
```

### Step 4: PostgreSQL first-time setup 

* **On Windows & macOS (Homebrew):** Your setup is likely done. The postgres user password is what you set during installation. You can skip to Step 5.

* **On Linux (Arch, Ubuntu, etc.):** You need to initialize the database and set a password for the postgres superuser.

    1. **Initialize the database (Arch only):** (Ubuntu/Debian do this automatically)

        ```bash 
        sudo -iu postgres initdb -D /var/lib/postgres/data
        ```
    2. **Start the service (arch only)**:

        ```Bash
        sudo systemctl start postgresql.service
        sudo systemctl enable postgresql.service
        ```
    3. **Set the superuser password (ALL Linux):** You must set a password for the postgres user to log in and create your game database.

        ```Bash
        # 1. Switch to the 'postgres' user
        sudo -iu postgres
        # 2. Open the psql tool
        psql
        # 3. In psql, set the password. Use single quotes!
        \password postgres

        -- It will ask you to enter a new password. Type it and press Enter.
        -- Type it again to confirm.

        # 4. Exit psql and go back to your normal user
        \q
        exit
        ```
### Step 5: Clone the repository
```bash 
git clone [EarthBound_like]
cd [earthbound_like]
```
### Step 6: Create you local database and user
Now you can log in as the postgres superuser (using the password you just set) and create your game database.

1. Open `psql`:
```Bash
psql -U postgres -h localhost
```
2. Run these SQL commands inside `psql`:
```SQL
-- 1. Create the blank database
CREATE DATABASE rpg;

-- 2. Create the user for the Java app
CREATE USER my_dev_user WITH PASSWORD 'my_strong_password_123';

-- 3. Give your new user full control over the new database
GRANT ALL PRIVILEGES ON DATABASE rpg TO my_dev_user;

-- 4. Exit psql
\q
```

### Step 7: Set your environments variables 
This application reads its database credentials from environment variables

* **On macOS/Linux:**
```Bash
export DB_URL="jdbc:postgresql://localhost:5432/rpg"
export DB_USER="my_dev_user"
export DB_PASSWORD="my_strong_password_123"
```

* **On Windows (PowerShell):**
```Bash 
$env:DB_URL = "jdbc:postgresql://localhost:5432/rpg"
$env:DB_USER = "my_dev_user"
$env:DB_PASSWORD = "my_strong_password_123"
```

* **On Windows (Command Prompt):**
```Bash 
set DB_URL="jdbc:postgresql://localhost:5432/rpg"
set DB_USER="my_dev_user"
set DB_PASSWORD="my_strong_password_123"
```

## -> How to run the "game" 
With your environment variables set, just run this command from the project's root folder:

```Bash
mvn exec:java
```

The first time you run this, Maven will download all the dependencies (Flyway, Jackson, etc.), and then Flyway will run all the V...sql migration scripts to build your database. After that, the game's main menu will appear (feature that will come).

## -> How to make database changes (workflow)

**Please, do not edit a migration script that has already been pushed!**
To make any change to the database (add a table, alter a column), you must create a **NEW** migration file.

1. Create a new `.sql` file in `src/main/resources/db/migration/`.

2. Name it with the next version number:
* V2__Some_new_feature.sql

* V3__Create_items_table.sql

3. Write _only_ the new sql commands in that file. 

4. Update the java code in `src/main/java/` to use these new changes.

5. Commit and push both you `.java` and `.sql` files.