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
├── auth.cfg.example
├── .gitignore
├── mssql-jdbc-11.2.0.jre11.jar
├── makefile
└── README.md
```

`GroupInterface.java` contains the console interface, JDBC connection logic, schema creation, CSV import routines, result formatting, and report queries.

## How to Run the Program

### 1. Set up SQL Server

Any SQL Server instance works (for example, the free SQL Server Express). The server must:

- accept TCP connections (Express ships with TCP turned off; enable it and set port 1433 in SQL Server Configuration Manager)
- allow SQL Server authentication ("mixed mode")
- have a dedicated database plus a login that owns it:

```sql
CREATE DATABASE moviedb;
CREATE LOGIN movieapp WITH PASSWORD = '<strong password>';
USE moviedb;
CREATE USER movieapp FOR LOGIN movieapp;
ALTER ROLE db_owner ADD MEMBER movieapp;
```

### 2. Configure the connection

```bash
cp auth.cfg.example auth.cfg
```

Edit `auth.cfg` with your `server`, `port`, `database`, `username` and `password`. The file is git-ignored.

### 3. Run the application

```bash
make run
```

Choose option `3` on first run to create the tables and import the CSV data.

### Clean compiled files

```bash
make clean
```

## Project Status

The source code, datasets, schema logic, and 19 reports are preserved.

The original university-hosted SQL Server is no longer available. The connection is now configured through `auth.cfg`, and the project has been restored and verified end to end on a local SQL Server 2022 Express instance: the full import completes (8,041 movies, 270,117 people, about 445,000 casting rows) and all 19 reports return results.

## Limitations

- The project uses SQL Server-specific syntax.
- People are matched by name, which may not always be unique.
- `Characters.csv` and `production_companies.csv` identify movies only by title. When several movies share a title, the importer picks the one whose cast overlaps the listed actors; the other same-titled movies get no character or budget data, because the source files contain none for them.
- The importer uses custom CSV parsing.
- Reports have not been comprehensively revalidated against a current database.
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
