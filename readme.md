# Movie Data Analysis

A Java and Microsoft SQL Server console application for exploring a large movie dataset through interactive analytical reports.

The program imports movies, people, characters, genres, languages, countries, production companies, and budgets into a relational database. Users can then ask focused questions about movie performance, creative collaboration, people, and geographic or language coverage.

The project demonstrates Java, JDBC, SQL Server, relational database design, CSV processing, batch inserts, and analytical SQL.

## Questions the Program Can Answer

The application provides 19 reports grouped into five analytical areas.

### Movie Performance

- What are the highest-rated movies?
- What are the lowest-rated movies?
- Which movies have the largest budgets within a selected genre?
- Which movies were released between two dates?
- What information is available about a specific movie?

### People and Roles

- What information is available about a specific person?
- What movies has a specific person worked on?
- What roles has a person had across movies?
- What are a person’s highest-rated movies?
- Which people born in a particular year are associated with highly rated movies?
- Which characters has an actor played?

### Collaboration Analysis

- Which actors appear most often in a selected genre?
- Which director-actor pairs have collaborated the most?
- Which actors have worked together most frequently?

### Production and Financial Analysis

- Which production companies have the highest average movie ratings?
- Which production companies are associated with the largest total budgets?

### Dataset Exploration

- Which movies are available in a selected language?
- Which movies are associated with a selected country?
- What genres exist in the dataset?
- What countries exist in the dataset?

These reports combine filtering, joins, grouping, aggregation, ranking, and relationship analysis to turn raw movie data into usable insights.

## Tech Stack

- Java
- JDBC
- Microsoft SQL Server
- Microsoft SQL Server JDBC Driver
- CSV data files
- Makefile

The application is a lightweight command-line program built directly with Java, JDBC, and SQL Server.

## Dataset

| File | Approximate Rows | Description |
| --- | ---: | --- |
| `Movies.csv` | 8,041 | Movies, ratings, runtime, release dates, languages, countries, genres, and credits |
| `People.csv` | 270,117 | People with birth and death dates |
| `Characters.csv` | 445,891 | Actor, character, and movie relationships |
| `production_companies.csv` | 8,041 | Movie production companies and budgets |

All required datasets are included in the repository.

## Database Design

The schema separates core entities from their relationships.

### Core Entities

- `Movie`
- `People`
- `Characters`
- `ProductionCompany`

### Relationship Tables

- `workedIn` — connects people to movies and records roles
- `playedBy` — connects people, characters, and movies
- `isIn` — connects characters to movies
- `MovieLanguages` — movie-language associations
- `MovieCountries` — movie-country associations
- `MovieGenre` — movie-genre associations
- `Finances` — movie-company associations and budgets

Genres, languages, and countries are stored in separate relationship tables rather than as repeated fields in the main movie table.

This represents a partially normalized relational design and supports flexible analytical queries across many-to-many relationships.

## Data Import Workflow

The import process is implemented in `GroupInterface.java` and runs from the application menu.

```text
People.csv
    ↓
Movies.csv
    ↓
Characters.csv
    ↓
production_companies.csv
```

The application:

1. Creates the database tables.
2. Loads core entities.
3. Splits multivalued fields such as genres, languages, and countries.
4. Connects people to movies based on their roles.
5. Loads characters and casting relationships.
6. Imports production companies and budget information.
7. Uses JDBC batch inserts to improve loading performance.

The populate option recreates the application tables, so it should only be used with a dedicated database.

## Example SQL Queries

### Highest-Rated Movies

```sql
SELECT TOP (?) Movie.title, Movie.rating
FROM Movie
ORDER BY Movie.rating DESC;
```

### Actors With the Most Appearances in a Genre

```sql
SELECT TOP (?) People.name, COUNT(*) AS appearances
FROM People
JOIN workedIn
  ON People.person_id = workedIn.person_id
JOIN Movie
  ON workedIn.imdb_id = Movie.imdb_id
JOIN MovieGenre
  ON Movie.imdb_id = MovieGenre.imdb_id
WHERE workedIn.role = 'actor'
  AND MovieGenre.Genre = ?
GROUP BY People.name
ORDER BY appearances DESC;
```

### Average Rating by Production Company

```sql
SELECT TOP (?)
       ProductionCompany.company_name,
       AVG(Movie.rating) AS average_rating
FROM Movie
JOIN Finances
  ON Movie.imdb_id = Finances.imdb_id
JOIN ProductionCompany
  ON Finances.company_id = ProductionCompany.company_id
GROUP BY ProductionCompany.company_name
ORDER BY average_rating DESC;
```

## Repository Structure

```text
.
├── GroupInterface.java
├── Movies.csv
├── People.csv
├── Characters.csv
├── production_companies.csv
├── auth.cfg.example          # copy to auth.cfg (git-ignored)
├── setup/
│   ├── configure-sqlexpress.ps1   # enables TCP 1433 + SQL logins on SQL Server Express
│   └── create-database.sql        # creates moviedb and the movieapp login
├── .gitignore
├── mssql-jdbc-11.2.0.jre11.jar
├── makefile
└── readme.md
```

`GroupInterface.java` contains the console interface, JDBC connection logic, schema creation, CSV import routines, result formatting, and report queries.

## How to Run the Program

### Prerequisites

- **Java JDK 17 or newer** (`java -version` and `javac -version` should both work)
- **Microsoft SQL Server**: the free SQL Server 2022 Express edition is enough; its 10 GB limit is far more than this dataset needs
- **sqlcmd**, the SQL Server command-line tool, to create the database
- **GNU make** (optional; you can run `javac`/`java` directly instead, see step 5)

The JDBC driver (`mssql-jdbc-11.2.0.jre11.jar`) is already in the repository.

### 1. Install SQL Server Express

**Windows** (from PowerShell or Command Prompt; approve the administrator prompt):

```powershell
winget install --id Microsoft.SQLServer.2022.Express --silent --accept-package-agreements
winget install --id Microsoft.Sqlcmd
```

Or download the installer from Microsoft's [SQL Server downloads](https://www.microsoft.com/sql-server/sql-server-downloads) page, choose **Express**, then **Basic** installation.

> **If setup fails** with an error about *Microsoft ODBC Driver 17 for SQL Server* (error 1706), a newer copy of that driver is already installed and blocks setup. Uninstall "Microsoft ODBC Driver 17 for SQL Server" in *Settings > Apps*, run the SQL Server install again, and then reinstall the latest driver with `winget install Microsoft.msodbcsql.17`.

**macOS / Linux** (Docker), instead of steps 1 and 2:

```bash
docker run -d --name moviedb-sql -p 1433:1433 \
  -e ACCEPT_EULA=Y -e MSSQL_SA_PASSWORD='<strong sa password>' \
  mcr.microsoft.com/mssql/server:2022-latest
```

The container already accepts TCP connections on port 1433 and SQL Server logins.

### 2. Allow the app to connect (Windows)

SQL Server Express installs with TCP connections and SQL Server logins turned off, so the app cannot connect yet. From an **Administrator** PowerShell in the repository folder:

```powershell
powershell -ExecutionPolicy Bypass -File setup\configure-sqlexpress.ps1
```

The script turns on TCP port 1433 and SQL Server authentication, then restarts SQL Server. (To do it by hand instead: in *SQL Server Configuration Manager*, enable TCP/IP for SQLEXPRESS with TCP Port 1433; in *SSMS*, set *Server authentication* to "SQL Server and Windows Authentication mode"; restart the service.)

### 3. Create the database and app login

Pick a strong password for the app login and run:

```bash
# Windows, using your Windows administrator login
sqlcmd -S tcp:localhost,1433 -E -C -i setup/create-database.sql -v AppPassword="<strong password>"

# Docker, using the sa login
sqlcmd -S tcp:localhost,1433 -U sa -P "<strong sa password>" -C -i setup/create-database.sql -v AppPassword="<strong password>"
```

This creates the `moviedb` database and a `movieapp` login that owns it.

### 4. Configure the connection

```bash
cp auth.cfg.example auth.cfg      # Windows Command Prompt: copy auth.cfg.example auth.cfg
```

Set `password` in `auth.cfg` to the app password from step 3. The defaults (`localhost`, port `1433`, database `moviedb`, user `movieapp`) match the steps above. `auth.cfg` is git-ignored, so your password stays on your machine.

### 5. Run the application

```bash
make run
```

Without make:

```bash
javac GroupInterface.java
java -Dfile.encoding=UTF-8 -cp ".;mssql-jdbc-11.2.0.jre11.jar" GroupInterface   # Windows
java -Dfile.encoding=UTF-8 -cp ".:mssql-jdbc-11.2.0.jre11.jar" GroupInterface   # macOS / Linux
```

On first run choose option **3** to create the tables and import the CSV data (about 2–4 minutes). Then choose option **1** to run reports.

### Clean compiled files

```bash
make clean
```

### Troubleshooting

| Message | Fix |
| --- | --- |
| `Could not connect ... The TCP/IP connection to the host localhost, port 1433 has failed` | SQL Server isn't running or TCP is off; redo step 2, or start the `SQL Server (SQLEXPRESS)` service |
| `Login failed for user 'movieapp'` | Password in `auth.cfg` doesn't match step 3, or SQL Server authentication is off (step 2) |
| `Cannot open database "moviedb"` | Run step 3 |
| `Could not find config file.` | Create `auth.cfg` (step 4) in the folder you run the app from |

## Project Status

The source code, datasets, schema logic, and 19 reports are preserved.

The original university-hosted SQL Server is no longer available. The connection is now configured through `auth.cfg`, and the project has been restored and verified end to end on a local SQL Server 2022 Express instance: the full import completes (8,041 movies, 270,117 people, about 445,000 casting rows) and all 19 reports return results.

## Limitations

- The project uses SQL Server-specific syntax.
- Movies.csv and Characters.csv credit people by name only, and about 2,350 names belong to more than one person. The importer picks the person who was alive around the movie's release (born before it, not dead more than five years before it), preferring the one who fits most of that name's credits, so near-duplicate records of one person collapse onto a single record.
- The source data contains implausible dates: many people in `People.csv` have birth or death dates that conflict with their credits (for example, credited in films released years after their recorded death), and some movies have wrong release dates. These are kept as-is because the repository has no reference data to correct them.
- `Characters.csv` and `production_companies.csv` identify movies only by title. When several movies share a title, the importer picks the one whose cast overlaps the listed actors; the other same-titled movies get no character or budget data, because the source files contain none for them.
- The importer uses custom CSV parsing.
- The application is a console program without a web or API layer.

## Skills Demonstrated

- Java application development
- JDBC connectivity
- SQL Server schema design
- Relational modeling and normalization
- CSV parsing and data transformation
- Batch database inserts
- Transaction and connection management
- SQL joins, grouping, and aggregation
- Analytical reporting
- Interactive command-line interface development
- Working with large datasets
- Identifying and documenting legacy database environment dependencies
