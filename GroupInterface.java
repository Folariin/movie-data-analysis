/* 
  GROUP 5
    Ogundimu, Opeyemi (OGUNDIMO)  - 7952705
    Daniel Nwogo (NWOGOD) - 7931833
    Amusat, Farouk (AMUSATF) -7937952
 */

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class GroupInterface {
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\033[0m";
    private static Scanner console = null;

    public static void main(String[] args) throws Exception {
        // startup sequence
        console = new Scanner(System.in);
        MyDatabase db = new MyDatabase(console);
        runConsole(db);

        System.out.println("Exiting...");
    }

    public static void runConsole(MyDatabase db) {
        // Scanner console = new Scanner(System.in);
        System.out.println("Welcome to The Movie App!");
        String line = null;
        int option = -1;
        while (option != 4) {
            if (option == -1) {
                printHelpMenu();
                System.out.print(GREEN + "Type the Corresponding number to navigate through the menu: " + RESET);
                line = console.nextLine().trim();
                try {
                    option = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    System.out.println("Enter a number!");
                }
            }
            if (option > 4 || option < 1) {
                System.out.println("Oops, Don't have that option available. Try again");
            } else if (option == 1) {
                int qoption = -1;
                executeQuery();
                boolean exit = false;
                boolean helpMenuReturn = false;
                while (!exit) {
                    System.out.print(GREEN + "Enter the query number: " + RESET);
                    line = console.nextLine().trim();
                    try {
                        if (line.equalsIgnoreCase("H")) {
                            option = -1;
                            helpMenuReturn = true;
                            exit = true;
                        } else {
                            qoption = Integer.parseInt(line);
                            exit = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("That's not even a number or \"H\".");
                    }
                }
                if (!helpMenuReturn) {
                    printBorders("Query Parameters");
                    switch (qoption) {
                        /* QUERY PARAMETERS point */
                        case 1:
                            System.out.println("Getting the Actor with most appearances in a genre.");
                            int l1 = getConsoleInteger("How Many would you like to see: ");
                            System.out.print("Please enter a genre: ");
                            String genre = console.nextLine().trim();
                            db.actorWithMostAppInGenre(genre, l1);
                            break;
                        case 2:
                            System.out.println("Getting the Top director-actor collaborations.");
                            int l2 = getConsoleInteger("How Many would you like to see: ");
                            db.directorsAndActorsCollab(l2);
                            break;
                        case 3:
                            System.out.println("Getting the Highest-rated movies.");
                            int l3 = getConsoleInteger("How Many would you like to see: ");
                            db.highestRatedMovies(l3);
                            break;
                        case 4:
                            System.out.println("Getting the Highest-grossing movies by genre.");
                            int l4 = getConsoleInteger("How Many would you like to see: ");
                            System.out.print("Enter a genre: ");
                            genre = console.nextLine().trim();
                            db.topGrossingMoviesByGenre(l4, genre);
                            break;
                        case 5:
                            System.out.println("Getting the Average ratings by production companies.");
                            int l5 = getConsoleInteger("How many would you like to see: ");
                            db.averageRatingOfMoviesByProductionCompanies(l5);
                            break;
                        case 6:
                            System.out.println("Getting the Most frequent actor collaborations.");
                            int l6 = getConsoleInteger("How many would you like to see: ");
                            db.mostFrequentCollabActors(l6);
                            break;
                        case 7:
                            System.out
                                    .println("Getting the Average ratings by person (director/actor/writer/producer).");
                            System.out.print("Please enter a Name: ");
                            String name = console.nextLine().trim();
                            db.averageRatingOfMoviesInvolvingPeople(name);
                            break;
                        case 8:
                            System.out.println("Getting the Movies in a specific language.");
                            int l8 = getConsoleInteger("How many would you like to see: ");
                            System.out.print("Please enter a Language: ");
                            String lang = console.nextLine().trim();
                            db.moviesInLanguage(l8, lang);
                            break;
                        case 9:
                            System.out.println("Getting the Information about a person");
                            System.out.print("Please enter a Name (be as specific as you Can): ");
                            String x9 = console.nextLine().trim();
                            db.getPersonInfo(x9);
                            break;
                        case 10:
                            System.out.println("Getting the Worst Rated Movies");
                            int l10 = getConsoleInteger("How many would you like to see: ");
                            db.getWorstRatedMovies(l10);
                            break;
                        case 11:
                            System.out.println("Getting the Characters played by an actor and its movie");
                            System.out.print("Please Enter a Name: ");
                            String actor = console.nextLine().trim();
                            db.CharactersPlayedByAnActor(actor);
                            break;
                        case 12:
                            /* A person's Top 5 movies and their participation in that movie */
                            System.out.println("Getting A Person Best Performing movies and their Role");
                            int num = getConsoleInteger("How Many would you like to see: ");
                            System.out.print("Please enter A Name: ");
                            String x12 = console.nextLine().trim();
                            db.aPersonTopMovies(x12, num);
                            break;
                        case 13:
                            System.out.println("Getting the Information about a Movie");
                            String x13 = null;
                            System.out.print("Please enter the Title of a movie: ");
                            x13 = console.nextLine().trim();
                            db.getMovieInfo(x13);
                            break;
                        case 14:
                            System.out.println("Getting the Top performing actors born in particular Year");
                            String year = getConsoleInteger("Enter a year in the format (yyyy): ") + "";
                            int lim = getConsoleInteger("How Many would you like to see: ");
                            db.TopPerformingActorsinBirthYear(year, lim);
                            break;
                        case 15:
                            System.out.println("Getting the Movies released between two dates (yyyy-mm-dd)");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            dateFormat.setLenient(false);
                            String date1 = null;
                            String date2 = null;
                            boolean valid = false;
                            while (!valid) {
                                try {
                                    System.out.print("Enter a first date(yyyy-mm-dd): ");
                                    date1 = console.nextLine().trim();
                                    System.out.print("Enter a second date(yyyy-mm-dd): ");
                                    date2 = console.nextLine().trim();
                                    // Date parsedDate = (Date) dateFormat.parse(date1);
                                    Date parsedDate = Date.valueOf(date1);
                                    Date parsedDate2 = Date.valueOf(date2);
                                    valid = true;
                                } catch (IllegalArgumentException e) {
                                    System.out.println(
                                            "Invalid date format. yyyy-MM-dd. Try Again");
                                }
                            }
                            int limit = getConsoleInteger("How many would you like to See: ");
                            db.MoviesBetweenTwoDates(date1, date2, limit);
                            break;
                        case 16:
                            System.out.println("Getting the Top Movies for a Country ");
                            System.out.print("Enter a Country: ");
                            String country = console.nextLine().trim();
                            int num16 = getConsoleInteger("How many would you like to see: ");
                            db.TopMoviesForCountry(country, num16);
                            break;
                        case 17:
                            System.out.println("Getting Top spending Production Companies");
                            int count17 = getConsoleInteger("How many companies would you like to see: ");
                            db.TopSpendingProdCompany(count17);
                            break;
                        case 18:
                            System.out.println("Getting All Genres");
                            db.ListAllGenre();
                            break;
                        case 19:
                            System.out.println("Getting All Countries");
                            db.ListAllCountries();
                            break;
                        default:
                            System.out.println("Unknown Input.");
                            option = -1;
                            break;

                    }
                    System.out.print(GREEN + "Would you like to return to Help Menu (Yes(Y) / No(N)): " + RESET);
                    String input = console.nextLine().trim();
                    if (input.equalsIgnoreCase("Y") | (input.equalsIgnoreCase("Yes"))) {
                        option = -1;
                    } else if (input.equalsIgnoreCase("N") | (input.equalsIgnoreCase("No"))) {
                        option = 1;
                    } else {
                        System.out.println("Unexpected Input, Returning to Help Menu");
                        option = -1;
                    }
                }
            } else if (option == 2) {
                /* Delete Database */
                option = -1;
                System.out.print(GREEN + "ARE YOU SURE YOU WISH TO DELETE THE ENTIRE DATABASE(Yes(Y) / No(N)): ");
                String input = console.nextLine();
                if (input.equalsIgnoreCase("Y") | input.equalsIgnoreCase("Yes")) {
                    db.Delete();
                    System.out.println(GREEN + "Successfully Deleted the Database!" + RESET);
                } else if (input.equalsIgnoreCase("N") | input.equalsIgnoreCase("NO")) {
                    System.out.println("RETURNING TO HELP MENU");
                }
            } else if (option == 3) {
                option = -1;
                if (db.populate()) {
                    System.out.println(GREEN + "Successfully Populated the Database!" + RESET);
                } else {
                    System.out.println(RED + "Populating the database failed; see the error above." + RESET);
                }
                /* Repopulate Databse data */
            }
        }
        System.out.println("<<<Scene...and Curtains>>>");
        console.close();
    }

    private static int getConsoleInteger(String DisplayMessage) {
        int num = -1;
        boolean end = false;
        String input = null;
        while (!end) {
            System.out.print(DisplayMessage);
            input = console.nextLine().trim();
            try {
                num = Integer.parseInt(input);
                end = true;
            } catch (NumberFormatException e) {
                System.out.println("That's not even a number.");
            }
        }
        return num;
    }

    private static void printBorders(String heading) {
        int borderLength = 35; // Length of the border
        String border = "-".repeat(borderLength);
        border = RED + border + RESET;

        // Calculate padding for center alignment
        int padding = (borderLength - heading.length()) / 2;

        // Create the centered heading
        String centeredHeading = " ".repeat(padding) + heading;

        System.out.println(border);
        System.out.println(centeredHeading);
        System.out.println(border);
    }

    private static void printHelpMenu() {
        printBorders("Help Menu");

        String bullet = "●";
        System.out.println("1.Execute Query");
        System.out.println("  " + bullet + " Choose a query to run from the available list");
        System.out.println("2.Delete Database Data");
        System.out.println("  " + bullet + " Remove all current data from database");
        System.out.println("3.Repopulate Database Data");
        System.out.println("  " + bullet + " Add default data back to the database");
        System.out.println("4.Exit");
        System.out.println("  " + bullet + " Close the Interface");

    }

    private static void executeQuery() {
        printBorders("Execute Query");

        System.out.println("Select a query from the following options");
        System.out.println("01. Actor with most appearances in a genre.");
        System.out.println("02. Top director-actor collaborations.");
        System.out.println("03. Highest-rated movies.");
        System.out.println("04. Highest-grossing movies by genre.");
        System.out.println("05. Average ratings by production companies.");
        System.out.println("06. Most frequent actor collaborations.");
        System.out.println("07. Average ratings by person (director/actor/writer/producer).");
        System.out.println("08. Movies in a specific language.");
        System.out.println("09. Information about a person");
        System.out.println("10. Worst-Rated Movies");
        System.out.println("11. Characters played by An Actor");
        System.out.println("12. A Person's Top Movies and Their Role");
        System.out.println("13. Information about a Movie");
        System.out.println("14. Top performing actors born in particular Year");
        System.out.println("15. Movies Released between Two dates");
        System.out.println("16. Top Performing Movies for a Country");
        System.out.println("17. Top Spending Production companies");
        System.out.println("18. List All Genres");
        System.out.println("19. List All Countries");
        System.out.println("Enter H to return to Help menu");

    }
}

class MyDatabase {

    private Connection connection = null;
    private Statement statement = null;

    private Map<String, Integer> personCache = new HashMap<>();
    private Map<String, Integer> imdbIdMap = new HashMap<>();
    private final String GREEN = "\u001B[32m";
    private final String RESET = "\033[0m";
    private final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    Scanner console = null;

    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public MyDatabase(Scanner console) {
        this.console = console;
        setUp();
    }

    // public static void main(String[] args) {
    private void setUp() {

        Properties prop = new Properties();
        String fileName = "auth.cfg";
        try {
            FileInputStream configFile = new FileInputStream(fileName);
            prop.load(configFile);
            configFile.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find config file.");
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("Error reading config file.");
            System.exit(1);
        }
        String username = prop.getProperty("username");
        String password = prop.getProperty("password");

        if (username == null || password == null) {
            System.out.println("Username or password not provided.");
            System.exit(1);
        }

        String server = prop.getProperty("server", "localhost").trim();
        String port = prop.getProperty("port", "1433").trim();
        String database = prop.getProperty("database", "moviedb").trim();
        String encrypt = prop.getProperty("encrypt", "false").trim();
        String trustServerCertificate = prop.getProperty("trustServerCertificate", "true").trim();

        // Credentials go through Properties so special characters (e.g. ';') don't break the URL.
        String connectionUrl = "jdbc:sqlserver://" + server + ":" + port + ";"
                + "databaseName=" + database + ";"
                + "encrypt=" + encrypt + ";"
                + "trustServerCertificate=" + trustServerCertificate + ";"
                + "loginTimeout=30;";
        Properties connProps = new Properties();
        connProps.setProperty("user", username.trim());
        connProps.setProperty("password", password);

        try {
            connection = DriverManager.getConnection(connectionUrl, connProps);
            statement = connection.createStatement();

            System.out.println(GREEN + "CONNECTION SUCCESSFUL" + RESET + " (" + server + ":" + port + "/" + database + ")");

        } catch (SQLException e) {
            System.out.println(RED + "Could not connect to " + server + ":" + port + "/" + database + RESET);
            System.out.println("Reason: " + e.getMessage());
            System.out.println("Check the server/port/database/username/password values in auth.cfg.");
            System.exit(1);
        }
    }

    private void reportError(Exception e) {
        System.out.println(RED + "Database error: " + e.getMessage() + RESET);
    }

    public boolean populate() {
        personCache.clear();
        imdbIdMap.clear();

        try {

            String sql = "drop table if exists Finances;drop table if exists IsIn;drop table if exists workedIn;drop table if exists playedBy;drop table if exists MovieLanguages;drop table if exists MovieCountries;drop table if exists MovieGenre;drop table if exists ProductionCompany;drop table if exists Characters;drop table if exists People;drop table if exists Movie;"
                    + "CREATE TABLE Movie ( imdb_id INTEGER PRIMARY KEY,  title VARCHAR(200) NOT NULL, air_date DATE NOT NULL, runtime INTEGER NOT NULL, rating REAL NOT NULL); "
                    +
                    "CREATE TABLE ProductionCompany( company_id INTEGER PRIMARY KEY,company_name varchar(200));"
                    +
                    "CREATE TABLE Finances(imdb_id INTEGER REFERENCES Movie(imdb_id),  company_id INTEGER REFERENCES ProductionCompany(company_id), budget REAL);"
                    +
                    "CREATE TABLE People(person_id INTEGER PRIMARY KEY,name VARCHAR(200) NOT NULL,birthdate DATE NOT NULL,deathdate DATE);"
                    +
                    "CREATE TABLE Characters(character_id INTEGER PRIMARY KEY,character_name varchar(200));"
                    +
                    "CREATE TABLE isIn(imdb_id INTEGER REFERENCES Movie(imdb_id),character_id INTEGER REFERENCES Characters(character_id));"
                    +
                    "CREATE TABLE workedIn(imdb_id INTEGER REFERENCES Movie(imdb_id),person_id INTEGER REFERENCES people(person_id),role VARCHAR(50) NOT NULL);"
                    +
                    "CREATE TABLE playedBy( imdb_id INTEGER REFERENCES Movie(imdb_id),   person_id INTEGER REFERENCES people(person_id),   character_id INTEGER REFERENCES Characters(character_id));"
                    +
                    "CREATE TABLE MovieLanguages (imdb_id INT,Language VARCHAR(100),PRIMARY KEY (imdb_id, Language),FOREIGN KEY (imdb_id) REFERENCES Movie(imdb_id) ON DELETE CASCADE);"
                    +
                    "CREATE TABLE MovieCountries (imdb_id INT,Country VARCHAR(100),PRIMARY KEY (imdb_id, Country),FOREIGN KEY (imdb_id) REFERENCES Movie(imdb_id) ON DELETE CASCADE);"
                    +
                    "CREATE TABLE MovieGenre (imdb_id INT,Genre VARCHAR(100),PRIMARY KEY (imdb_id, Genre),FOREIGN KEY (imdb_id) REFERENCES Movie(imdb_id) ON DELETE CASCADE);";

            statement.executeUpdate(sql);
            long startTime = System.currentTimeMillis();
            connection.setAutoCommit(false); // Begin transaction
            readPeopleFast();
            connection.commit(); // Commit transaction
            connection.setAutoCommit(true);// reset
            long peopleEnd = System.currentTimeMillis();
            long duration = peopleEnd - startTime;
            System.out.println(GREEN + "COMPLETED UPLOADING PEOPLE" + RESET);
            System.out.println("People execution took: " + duration + " milliseconds");

            // Uploading Movie info
            startTime = System.currentTimeMillis();
            connection.setAutoCommit(false); // Begin transaction
            loadPersonCache(); // Preload person IDs
            readInMovieData();
            connection.commit(); // Commit transaction
            connection.setAutoCommit(true);// reset
            long endTime = System.currentTimeMillis();
            duration = endTime - startTime; // Time in milliseconds
            System.out.println(GREEN + "COMPLETED UPLOADING MOVIES" + RESET);
            System.out.println("Movies execution took: " + duration + " milliseconds");

            // Uploading Characters info
            startTime = System.currentTimeMillis();
            connection.setAutoCommit(false); // Begin transaction
            preloadImdbIDs(imdbIdMap);
            readCharacterData();
            connection.commit(); // Commit transaction
            connection.setAutoCommit(true);// reset
            endTime = System.currentTimeMillis();
            duration = endTime - startTime; // Time in milliseconds
            System.out.println(GREEN + "COMPLETED UPLOADING CHARACTERS" + RESET);
            System.out.println("Characters execution took: " + duration + " milliseconds");

            // Uploading PRoduction Companies info
            startTime = System.currentTimeMillis();
            connection.setAutoCommit(false); // Begin transaction
            readProdCompany();
            connection.commit(); // Commit transaction
            connection.setAutoCommit(true);// reset
            endTime = System.currentTimeMillis();
            duration = endTime - startTime; // Time in milliseconds
            System.out.println(GREEN + "COMPLETED UPLOADING PRODUCTION COMPANIES" +
                    RESET);
            System.out.println("Production Companies execution took: " + duration + " milliseconds");

            return true;
        } catch (Exception e) {
            reportError(e);
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e2) {
                reportError(e2);
            }
            return false;
        }
    }

    private void readPeopleFast() throws Exception {
        String csvFilePath = "People.csv";
        int batchSize = 2000;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8))) {

            String line = br.readLine(); // Skip header
            PreparedStatement personStmt = connection.prepareStatement(
                    "INSERT INTO People (person_id, name, birthdate, deathdate) VALUES (?, ?, ?, ?)");

            int countLine = 0;
            int batchCount = 0;

            while ((line = br.readLine()) != null) {
                countLine++;
                String[] columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // Trim and remove quotes
                for (int i = 0; i < columns.length; i++) {
                    columns[i] = columns[i].replaceAll("^\"|\"$", "").trim();
                }

                // Insert into PersonTest
                // person_id,Name,Birth Date,Death Date
                personStmt.setInt(1, Integer.parseInt(columns[0])); // person_id
                personStmt.setString(2, columns[1]); // name
                personStmt.setDate(3, Date.valueOf(columns[2]));
                if (columns.length == 3) {
                    personStmt.setDate(4, null);
                } else if (columns.length == 4) {
                    personStmt.setDate(4, Date.valueOf(columns[3]));
                }
                personStmt.addBatch();

                // Execute batch in intervals
                if (++batchCount % batchSize == 0) {
                    personStmt.executeBatch();
                }
            }

            // Execute remaining batches
            personStmt.executeBatch();

            // System.out.println(countLine + " People lines were read and inserted.");
            personStmt.close();
        } catch (Exception e) {
            throw new Exception("People.csv: " + e.getMessage(), e);
        }
    }

    private void loadPersonCache() throws SQLException {
        String query = "SELECT person_id, name FROM People";
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                personCache.put(rs.getString("name").trim(), rs.getInt("person_id"));
            }
        }
    }

    private void readInMovieData() throws Exception {
        String csvFilePath = "Movies.csv";
        int batchSize = 2000;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8))) {

            String line = br.readLine(); // Skip header
            PreparedStatement movieStmt = connection.prepareStatement(
                    "INSERT INTO Movie (imdb_id, title, air_date, runtime, rating) VALUES (?, ?, ?, ?, ?)");
            PreparedStatement langStmt = connection.prepareStatement(
                    "INSERT INTO MovieLanguages (imdb_id, language) VALUES (?, ?)");
            PreparedStatement countryStmt = connection.prepareStatement(
                    "INSERT INTO MovieCountries (imdb_id, country) VALUES (?, ?)");
            PreparedStatement genreStmt = connection.prepareStatement(
                    "INSERT INTO MovieGenre (imdb_id, genre) VALUES (?, ?)");
            PreparedStatement workedInStmt = connection.prepareStatement(
                    "INSERT INTO workedIn (imdb_id, person_id, role) VALUES (?, ?, ?)");

            int countLine = 0;
            int batchCount = 0;

            while ((line = br.readLine()) != null) {
                countLine++;
                String[] columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                // Trim and remove quotes
                for (int i = 0; i < columns.length; i++) {
                    columns[i] = columns[i].replaceAll("^\"|\"$", "").trim();
                }

                // Insert into Movie
                movieStmt.setInt(1, Integer.parseInt(columns[3])); // imdb_id
                movieStmt.setString(2, columns[0]); // title
                movieStmt.setDate(3, Date.valueOf(columns[11])); // air_date
                movieStmt.setInt(4, Integer.parseInt(columns[2])); // runtime
                movieStmt.setDouble(5, Double.parseDouble(columns[1])); // rating
                movieStmt.addBatch();

                // Insert into MovieLanguages
                for (String lang : columns[4].split(",")) {
                    langStmt.setInt(1, Integer.parseInt(columns[3]));
                    langStmt.setString(2, lang.trim());
                    langStmt.addBatch();
                }

                // Insert into MovieCountries
                for (String country : columns[5].split(",")) {
                    countryStmt.setInt(1, Integer.parseInt(columns[3]));
                    countryStmt.setString(2, country.trim());
                    countryStmt.addBatch();
                }

                // Insert into MovieGenre
                for (String genre : columns[6].split(",")) {
                    genreStmt.setInt(1, Integer.parseInt(columns[3]));
                    genreStmt.setString(2, genre.trim());
                    genreStmt.addBatch();
                }

                // Insert into workedIn
                for (int i = 7; i <= 10; i++) {
                    String role = switch (i) {
                        case 7 -> "director";
                        case 8 -> "producer";
                        case 9 -> "writer";
                        case 10 -> "actor";
                        default -> null;
                    };
                    for (String name : columns[i].split(",")) {
                        int personId = personCache.getOrDefault(name.trim(), -1);
                        if (personId != -1) {
                            workedInStmt.setInt(1, Integer.parseInt(columns[3]));
                            workedInStmt.setInt(2, personId);
                            workedInStmt.setString(3, role);
                            workedInStmt.addBatch();
                        }
                    }
                }

                // Execute batch in intervals
                if (++batchCount % batchSize == 0) {
                    movieStmt.executeBatch();
                    langStmt.executeBatch();
                    countryStmt.executeBatch();
                    genreStmt.executeBatch();
                    workedInStmt.executeBatch();
                }
            }

            // Execute remaining batches
            movieStmt.executeBatch();
            langStmt.executeBatch();
            countryStmt.executeBatch();
            genreStmt.executeBatch();
            workedInStmt.executeBatch();

            // System.out.println(countLine + " Movie lines were read and inserted.");
            movieStmt.close();
            langStmt.close();
            countryStmt.close();
            genreStmt.close();
            workedInStmt.close();

        } catch (Exception e) {
            throw new Exception("Movies.csv: " + e.getMessage(), e);
        }
    }

    private void readCharacterData() throws Exception {
        String csvFilePath = "Characters.csv";
        Map<String, Integer> characterIdMap = new HashMap<>();
        Map<String, Integer> personIdMap = new HashMap<>();
        AtomicInteger idCounter = new AtomicInteger(1);
        int batchSize = 3000;

        preloadPersonIDs(personIdMap);

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8))) {
            String line = br.readLine(); // Skip header
            List<String[]> rows = new ArrayList<>();
            Set<String> uniqueCharacters = new HashSet<>();

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (columns.length < 3)
                    continue;
                rows.add(columns);

                String characterName = columns[1].trim().replaceAll("^\"|\"$", "");
                uniqueCharacters.add(characterName);
            }

            // Assign IDs to unique characters
            for (String characterName : uniqueCharacters) {
                characterIdMap.put(characterName, idCounter.getAndIncrement());
            }

            // Batch insert characters
            try (PreparedStatement characterStmt = connection.prepareStatement(
                    "INSERT INTO Characters (character_id, character_name) VALUES (?, ?)")) {
                for (Map.Entry<String, Integer> entry : characterIdMap.entrySet()) {
                    characterStmt.setInt(1, entry.getValue());
                    characterStmt.setString(2, entry.getKey());
                    characterStmt.addBatch();

                    if (idCounter.get() % batchSize == 0) {
                        characterStmt.executeBatch();
                    }
                }
                characterStmt.executeBatch(); // Insert remaining
            }

            // Build relationships
            try (PreparedStatement playedByStmt = connection.prepareStatement(
                    "INSERT INTO playedBy (character_id, person_id,imdb_id) VALUES (?, ?, ?);");
                    PreparedStatement isInStmt = connection.prepareStatement(
                            "INSERT INTO isIn (character_id, imdb_id) VALUES (?, ?);")) {
                int batchCount = 0;

                for (String[] columns : rows) {
                    String characterName = columns[1].trim().replaceAll("^\"|\"$", "");
                    String movieTitle = columns[2].trim().replaceAll("^\"|\"$", "");
                    String personName = columns[0].trim().replaceAll("^\"|\"$", "");

                    Integer characterId = characterIdMap.get(characterName);
                    // Integer personId = personIdMap.getOrDefault(personName, -1);
                    Integer personId = personIdMap.get(personName);
                    // Integer imdbId = imdbIdMap.getOrDefault(movieTitle, -1);
                    Integer imdbId = imdbIdMap.get(movieTitle);
                    // System.out.println(personId + "-" + columns[0] + " : " + columns[1] + "-" +
                    // characterId);

                    // if (personId == -1 || imdbId == -1)
                    if (personId == null || imdbId == null)
                        continue; // Log skipped rows for debugging

                    playedByStmt.setInt(1, characterId);
                    playedByStmt.setInt(2, personId);
                    playedByStmt.setInt(3, imdbId);
                    playedByStmt.addBatch();

                    isInStmt.setInt(1, characterId);
                    isInStmt.setInt(2, imdbId);
                    isInStmt.addBatch();

                    if (++batchCount % batchSize == 0) {
                        playedByStmt.executeBatch();
                        isInStmt.executeBatch();
                    }
                }

                playedByStmt.executeBatch();
                isInStmt.executeBatch();
            }

            connection.commit(); // Commit all changes
            connection.setAutoCommit(true);// reset
        } catch (Exception e) {
            throw new Exception("Characters.csv: " + e.getMessage(), e);
        }
    }

    private void readProdCompany() throws Exception {
        String csvFilePath = "production_companies.csv";
        Map<String, Integer> companiesMap = new HashMap<>(); // Map to store company name -> ID
        AtomicInteger idCounter = new AtomicInteger(1); // Auto-increment ID for companies
        int batchSize = 2000; // Batch size for batch processing
        Set<String> uniqueCompanies = new HashSet<>();// set for names of companies
        List<String[]> rows = new ArrayList<>(); // Store all rows for later processing

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8))) {

            connection.setAutoCommit(false); // Disable auto-commit for transaction management

            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (columns.length < 3)
                    continue;

                rows.add(columns); // Store for later
                String[] companies = columns[1].split(",");

                for (String company : companies) {
                    company = company.trim().replaceAll("^\"|\"$", "");
                    if (company.length() > 0) {
                        uniqueCompanies.add(company);
                    }
                }
            }

            for (String company : uniqueCompanies) {
                company = company.trim();
                companiesMap.put(company, idCounter.getAndIncrement());
            }

            // Insert companies into ProductionCompany table
            try (PreparedStatement prodStmt = connection.prepareStatement(
                    "INSERT INTO ProductionCompany (company_id, company_name) VALUES (?, ?)")) {
                int batchCount = 0;
                for (Map.Entry<String, Integer> entry : companiesMap.entrySet()) {
                    String name = entry.getKey();
                    int id = entry.getValue();
                    prodStmt.setInt(1, id);
                    prodStmt.setString(2, name);
                    prodStmt.addBatch();

                    if (++batchCount % batchSize == 0) {
                        prodStmt.executeBatch();
                    }
                }
                prodStmt.executeBatch(); // Execute remaining batches
                prodStmt.close();
            }

            // Insert finances into Finances table
            try (PreparedStatement financeStmt = connection.prepareStatement(
                    "INSERT INTO Finances (company_id, imdb_id, budget) VALUES (?, ?, ?)")) {
                int batchCount = 0;
                for (String[] columns : rows) {
                    String movieTitle = columns[0].trim().replaceAll("^\"|\"$", "");
                    double budget = Double.parseDouble(columns[2].trim().replaceAll("^\"|\"$", ""));

                    Integer imdbId = imdbIdMap.get(movieTitle);
                    if (imdbId == null) {
                        System.err.println("Missing IMDb ID for movie: " + movieTitle);
                        continue;
                    }

                    String[] companies = columns[1].split(",");
                    for (String company : companies) {
                        company = company.replaceAll("^\"|\"$", "").trim();
                        if (company.length() > 0) {
                            Integer companyId = companiesMap.get(company);

                            if (companyId == null) {
                                System.err.println("Company not found in map: " + company);
                                continue;
                            }

                            financeStmt.setInt(1, companyId);
                            financeStmt.setInt(2, imdbId);
                            financeStmt.setDouble(3, budget);
                            financeStmt.addBatch();
                        }

                        if (++batchCount % batchSize == 0) {
                            financeStmt.executeBatch();
                        }
                    }
                }
                financeStmt.executeBatch(); // Execute remaining batches
                financeStmt.close();
            }

            connection.commit(); // Commit all changes
            connection.setAutoCommit(true);// reset

        } catch (Exception e) {
            throw new Exception("production_companies.csv: " + e.getMessage(), e);
        }
    }

    // Preload IDs for optimization
    private void preloadPersonIDs(Map<String, Integer> personIdMap) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT person_id, name FROM People")) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                int id = rs.getInt("person_id");
                personIdMap.put(name, id);
            }
        }
    }

    private void preloadImdbIDs(Map<String, Integer> imdbIdMap) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT imdb_id, title FROM Movie")) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                imdbIdMap.put(rs.getString("title"), rs.getInt("imdb_id"));
            }
        }
    }

    // 1✓ //UPDATE: NOW PRINTS THE TOP 5 for that genre
    public void actorWithMostAppInGenre(String genre, int count) {
        try {
            String sql = "SELECT TOP (" + count
                    + ") [name] , COUNT(*) AS appearances  FROM people INNER JOIN workedIn ON People.person_id = workedIn.person_id AND workedIn.role = 'actor' INNER JOIN Movie ON workedIn.imdb_id = Movie.imdb_id INNER JOIN MovieGenre ON Movie.imdb_id = MovieGenre.imdb_id WHERE MovieGenre.Genre = ? GROUP BY People.name ORDER BY appearances DESC";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, genre);

            ResultSet resultSet = statement.executeQuery();

            boolean found = false;
            if (!isMyResultSetEmpty(resultSet)) {
                found = true;
                String[] queryTitle = { "Name", "appearances" };
                String[] dataType = { "string", "int" };
                String[] header = { "Name", "Number of Appearances" };
                printTable(resultSet, header, dataType, queryTitle);
            }
            if (!found) {
                System.out.println("No matching records found.");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            reportError(e);
        }
    }

    // 2✓***
    public void directorsAndActorsCollab(int limit) {
        String sql = "WITH Actors AS (SELECT People.person_id,name,imdb_id FROM [People] INNER JOIN [workedIn] on workedIn.person_id = People.person_id WHERE [role] = 'actor'),Directors AS (  SELECT People.person_id, name,imdb_id FROM [People] INNER JOIN [workedIn] on workedIn.person_id = People.person_id WHERE workedIn.role = 'director') SELECT TOP ("
                + limit
                + ") Actors.name AS actor_name,Directors.name AS director_name,COUNT(Movie.imdb_id) AS most_collaborations FROM Movie LEFT JOIN Actors ON Actors.imdb_id = Movie.imdb_id LEFT JOIN Directors ON Directors.imdb_id = Movie.imdb_id WHERE Directors.person_id != Actors.person_id GROUP BY Actors.name,Directors.name ORDER BY most_collaborations DESC;";

        try {
            ResultSet resultSet = statement.executeQuery(sql);

            boolean found = false;

            if (!isMyResultSetEmpty(resultSet)) {
                found = true;
                String[] queryTitle = { "actor_name", "director_name", "most_collaborations" };
                String[] dataType = { "string", "string", "int" };
                String[] header = { "Actor", "Director", "Most Collaborations" };
                printTable(resultSet, header, dataType, queryTitle);
            }

            if (!found) {
                System.out.println("No matching records found.");
            }
            resultSet.close();
        } catch (SQLException e) {
            reportError(e);
        }
    }

    // 3✓
    public void highestRatedMovies(int limit) {
        String sql = "SELECT TOP (" + limit + ") Movie.title, Movie.rating " +
                "FROM Movie " +
                "ORDER BY Movie.rating DESC";

        try {

            ResultSet resultSet = statement.executeQuery(sql);

            boolean found = false;
            if (!isMyResultSetEmpty(resultSet)) {
                found = true;
                String[] header = { "Title", "Rating" };
                String[] dataType = { "string", "double" };
                String[] queryTitle = { "Title", "Rating" };
                printTable(resultSet, header, dataType, queryTitle);
            }

            if (!found) {
                System.out.println("No matching records found.");
            }
            resultSet.close();
        } catch (SQLException e) {
            reportError(e);
        }
    }

    // 4✓
    public void averageRatingOfMoviesByProductionCompanies(int limit) {
        String sql = "SELECT TOP (" + limit + ") " +
                "ProductionCompany.company_name AS companyName, AVG(Movie.rating) AS average_rating " +
                "FROM Movie " +
                "INNER JOIN Finances ON Movie.imdb_id = Finances.imdb_id " +
                "INNER JOIN ProductionCompany ON Finances.company_id = ProductionCompany.company_id " +
                "GROUP BY ProductionCompany.company_name " +
                "ORDER BY average_rating DESC";

        try {

            try (ResultSet resultSet = statement.executeQuery(sql)) {
                boolean found = false;
                if (!isMyResultSetEmpty(resultSet)) {
                    found = true;
                    String[] queryTitle = { "CompanyName", "average_rating" };
                    String[] dataType = { "string", "double" };
                    String[] header = { "Company", "Average Rating" };
                    printTable(resultSet, header, dataType, queryTitle);
                }

                if (!found) {
                    System.out.println("No matching records found.");
                }
            }
        } catch (SQLException e) {
            reportError(e);
        }
    }

    // 5 ✓
    public void topGrossingMoviesByGenre(int limit, String genre) {
        String sql = "SELECT DISTINCT TOP (" + limit
                + ") Movie.imdb_id,Movie.title,budget,Genre FROM Movie JOIN MovieGenre ON MovieGenre.imdb_id = Movie.imdb_id JOIN Finances ON Movie.imdb_id = Finances.imdb_id WHERE Genre = ? ORDER BY Finances.budget DESC;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, genre);

            try (ResultSet resultSet = statement.executeQuery()) {
                boolean found = false;

                if (!isMyResultSetEmpty(resultSet)) {
                    found = true;
                    String[] header = { "Title", "Budget($)" };
                    String[] dataType = { "string", "double" };
                    String[] queryTitle = { "Title", "budget" };
                    printTable(resultSet, header, dataType, queryTitle);
                }

                if (!found) {
                    System.out.println("No matching records found.");
                }
            }
        } catch (SQLException e) {
            reportError(e);
        }
    }

    // 6 ✓
    public void mostFrequentCollabActors(int limit) {
        System.out.println(GREEN + "This may take a while :/" + RESET);
        String sql = "WITH Actor1 AS (SELECT People.person_id, People.name,imdb_id FROM People INNER JOIN workedIn on People.person_id=workedIn.person_id WHERE workedIn.role='Actor'), Actor2 AS ( SELECT People.person_id, People.name,imdb_id FROM People INNER JOIN workedIn on People.person_id=workedIn.person_id WHERE workedIn.role='Actor') SELECT TOP ("
                + limit
                + ") Actor1.name AS first_actor_name, Actor2.name AS second_actor_name, COUNT(Actor1.imdb_id) AS most_collaborations FROM Actor1 INNER JOIN Actor2 on Actor1.imdb_id=Actor2.imdb_id WHERE Actor1.person_id < Actor2.person_id GROUP BY Actor1.person_id, Actor2.person_id,Actor1.name,Actor2.name ORDER BY most_collaborations DESC;";

        try {

            try (ResultSet resultSet = statement.executeQuery(sql)) {
                boolean found = false;
                if (!isMyResultSetEmpty(resultSet)) {
                    found = true;
                    String[] queryTitle = { "first_actor_name", "second_actor_name", "most_collaborations" };
                    String[] dataType = { "string", "string", "int" };
                    String[] header = { "Actor 1", "Actor 2", "Most Collaborations" };
                    printTable(resultSet, header, dataType, queryTitle);
                }

                if (!found) {
                    System.out.println("No matching records found.");
                }
            }
        } catch (SQLException e) {
            reportError(e);
        }
    }

    // 7✓
    public void averageRatingOfMoviesInvolvingPeople(String name) {
        String sql = "SELECT People.name, AVG(Movie.rating) AS average_rating FROM Movie INNER JOIN workedIn ON Movie.imdb_id = workedIn.imdb_id INNER JOIN People ON workedIn.person_id = People.person_id WHERE People.name = ?  GROUP BY People.name";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                boolean found = false;

                if (!isMyResultSetEmpty(resultSet)) {
                    found = true;
                    String[] header = { "Name", "Average Rating" };
                    String[] dataType = { "string", "double" };
                    String[] queryTitle = { "Name", "average_rating" };
                    printTable(resultSet, header, dataType, queryTitle);
                }

                if (!found) {
                    System.out.println("No records found for the specified person.");
                }
            }
        } catch (SQLException e) {
            reportError(e);
        }
    }

    // 8 ✓
    public void moviesInLanguage(int limit, String language) {
        String sql = "SELECT TOP (" + limit + ") Movie.title " +
                "FROM Movie " +
                "JOIN MovieLanguages ON Movie.imdb_id = MovieLanguages.imdb_id " +
                "WHERE MovieLanguages.language = ? " +
                "ORDER BY Movie.rating DESC";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, language);

            try (ResultSet resultSet = statement.executeQuery()) {
                boolean found = false;

                if (!isMyResultSetEmpty(resultSet)) {
                    found = true;
                    String[] header = { "Title" };
                    String[] datatype = { "String" };
                    String[] queryTitle = { "Title" };
                    printTable(resultSet, header, datatype, queryTitle);
                }

                if (!found) {
                    System.out.println("No matching movies found in the specified language.");
                }
            }
        } catch (SQLException e) {
            reportError(e);
        }
    }

    public static boolean isMyResultSetEmpty(ResultSet rs) throws SQLException {
        return (!rs.isBeforeFirst() && rs.getRow() == 0);
    }

    public void printTable(ResultSet rs, String[] headers, String[] dataType, String[] queryTitle) {
        int pageSize = 10;
        if (rs != null) {
            if (dataType.length == headers.length && headers.length == queryTitle.length) {
                try {
                    List<String[]> data = new ArrayList<>();
                    int[] columnWidths = new int[headers.length];

                    // Initialize columnWidths based on header lengths
                    for (int i = 0; i < headers.length; i++) {
                        columnWidths[i] = headers[i].length();
                    }

                    // First pass: Calculate max length for each column based on data
                    while (rs.next()) {
                        String[] rowData = new String[headers.length];
                        for (int i = 0; i < headers.length; i++) {
                            String value = "";
                            if (dataType[i].equalsIgnoreCase("int")) {
                                value = String.valueOf(rs.getInt(queryTitle[i]));
                            } else if (dataType[i].equalsIgnoreCase("string")) {
                                value = rs.getString(queryTitle[i]);
                            } else if (dataType[i].equalsIgnoreCase("double")) {
                                value = String.format("%.2f", rs.getDouble(queryTitle[i]));
                            }
                            if (value == null) {
                                value = "";
                            }
                            rowData[i] = value;

                            columnWidths[i] = Math.max(columnWidths[i], value.length());
                        }
                        data.add(rowData);
                    }

                    int totalRows = data.size();
                    int totalPages = (int) Math.ceil((double) totalRows / pageSize);
                    int currentPage = 1;

                    boolean exit = false;
                    while (!exit) {
                        // Display the current page
                        int startRow = (currentPage - 1) * pageSize;
                        int endRow = Math.min(startRow + pageSize, totalRows);

                        // Print top border
                        StringBuilder borderLine = new StringBuilder();
                        for (int width : columnWidths) {
                            borderLine.append(GREEN + "+" + RESET).append("-".repeat(width + 2));
                        }
                        borderLine.append(GREEN + "+" + RESET);

                        System.out.println(borderLine.toString());

                        // Print headers
                        StringBuilder headerLine = new StringBuilder("|");
                        for (int i = 0; i < headers.length; i++) {
                            headerLine.append(" ")
                                    .append(String.format(BLUE + "%-" + columnWidths[i] + "s" + RESET, headers[i]))
                                    .append(" |");
                        }
                        System.out.println(headerLine.toString());
                        System.out.println(borderLine.toString());

                        // Print rows for the current page
                        for (int i = startRow; i < endRow; i++) {
                            StringBuilder rowLine = new StringBuilder("|");
                            String[] row = data.get(i);
                            for (int j = 0; j < row.length; j++) {
                                rowLine.append(" ")
                                        .append(String.format("%-" + columnWidths[j] + "s", row[j]))
                                        .append(" |");
                            }
                            System.out.println(rowLine.toString());
                            System.out.println(borderLine.toString());
                        }

                        // Display pagination options
                        System.out.printf(
                                "Page %d of %d. Enter 'n' for next page, 'p' for previous page, or 'q' to quit: ",
                                currentPage, totalPages);
                        String input = console.nextLine().trim().toLowerCase();

                        if (input.equalsIgnoreCase("q")) {
                            exit = true;
                            ;
                        } else if (input.equalsIgnoreCase("n")) {
                            if (currentPage < totalPages) {
                                currentPage++;
                            } else {
                                System.out.println(RED + "You are already on the last page." + RESET);
                            }
                        } else if (input.equalsIgnoreCase("p")) {
                            if (currentPage > 1) {
                                currentPage--;
                            } else {
                                System.out.println(RED + "You are already on the first page." + RESET);
                            }
                        } else {
                            System.out.println("Invalid input. Please enter 'n', 'p', or 'q'.");
                        }
                    }
                } catch (SQLException e) {
                    reportError(e);
                }
            }
        }
    }

    public void Delete() {
        String sql = "drop table if exists Finances;                        drop table if exists IsIn;drop table if exists workedIn;drop table if exists playedBy;drop table if exists MovieLanguages;drop table if exists MovieCountries;drop table if exists MovieGenre;drop table if exists ProductionCompany;drop table if exists Characters;drop table if exists People;drop table if exists Movie;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            reportError(e);
        }
    }

    /*
     * Possible UPDATES
     * GET THE PRODUCTION COMPANIES FOR A PARTICULAR MOVIE
     * GET ALL THE DIRECTORs/PRODUCERs/ACTORs/WRITERs (10 Max for each)
     * ACTORS THAT HAVE PLAYED A PARTICULAR CHARACTER
     * ACTORS BORN BETWEEN TWO DATES
     * GET THE BUDGET OF A MOVIE
     * MOVIES WITH THE HIGHEST BUDGET
     * MOVIES INVOLVING A CERTAIN ACTOR (sort by average rating and limit to 15
     * rows)
     */

    public void getPersonInfo(String name) {
        name = "%" + name + "%";
        String sql = "SELECT People.name,birthdate,deathdate, AVG(Movie.rating) AS average_rating FROM Movie INNER JOIN workedIn ON Movie.imdb_id = workedIn.imdb_id INNER JOIN People ON workedIn.person_id = People.person_id WHERE People.name LIKE ? GROUP BY People.name,birthdate,deathdate ORDER BY average_rating DESC;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            boolean found = false;

            if (!isMyResultSetEmpty(resultSet)) {
                found = true;
                String[] queryTitle = { "name", "birthdate", "deathdate" };
                String[] dataType = { "string", "string", "string" };
                String[] header = { "Name", "Birthdate", "Deathdate" };
                printTable(resultSet, header, dataType, queryTitle);
            }

            if (!found) {
                System.out.println("No records found.");
            }
            resultSet.close();
        } catch (SQLException e) {
            reportError(e);
        }

    }

    public void getMovieInfo(String title) {
        title = "%" + title + "%";
        String sql = "SELECT imdb_id, title, air_date, runtime, rating FROM Movie WHERE title LIKE ? ORDER BY rating;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();

            boolean found = false;

            if (!isMyResultSetEmpty(resultSet)) {
                found = true;
                String[] queryTitle = { "imdb_id", "title", "air_date", "runtime", "rating" };
                String[] dataType = { "int", "string", "string", "int", "double" };
                String[] header = { "IMDB ID", "Title", "Air Date", "Runtime", "Rating" };
                printTable(resultSet, header, dataType, queryTitle);
            }

            if (!found) {
                System.out.println("No records found.");
            }
            resultSet.close();
        } catch (SQLException e) {
            reportError(e);
        }
    }

    public void CharactersPlayedByAnActor(String name) {
        name = "%" + name + "%";
        String sql = "SELECT DISTINCT name,Characters.character_name,title  FROM Characters Join playedBy ON playedBy.character_id = Characters.character_id join people on people.person_id=playedBy.person_id join movie on playedBy.imdb_id=movie.imdb_id WHERE name LIKE ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            boolean found = false;

            if (!isMyResultSetEmpty(resultSet)) {
                found = true;
                String[] queryTitle = { "name", "character_name", "Title" };
                String[] dataType = { "String", "String", "string" };
                String[] header = { "Name", "Movie Character", "Title" };
                printTable(resultSet, header, dataType, queryTitle);
            }

            if (!found) {
                System.out.println("No records found.");
            }
            resultSet.close();
        } catch (SQLException e) {
            reportError(e);
        }
    }

    public void getWorstRatedMovies(int limit) {
        String sql = "SELECT TOP(" + limit + ") title,rating FROM Movie ORDER BY rating ;";

        try {
            ResultSet resultSet = statement.executeQuery(sql);

            boolean found = false;

            if (!isMyResultSetEmpty(resultSet)) {
                found = true;
                String[] queryTitle = { "title", "rating" };
                String[] dataType = { "string", "double" };
                String[] header = { "Title", "Rating" };
                printTable(resultSet, header, dataType, queryTitle);
            }

            if (!found) {
                System.out.println("No records found.");
            }
            resultSet.close();
        } catch (SQLException e) {
            reportError(e);
        }
    }

    public void MoviesBetweenTwoDates(String date1, String date2, int limit) {
        String sql = "SELECT TOP (" + limit + ") * FROM MOVIE WHERE air_date BETWEEN ? AND ? ORDER BY rating DESC;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, date1);
            statement.setString(2, date2);
            ResultSet resultSet = statement.executeQuery();

            boolean found = false;

            if (!isMyResultSetEmpty(resultSet)) {
                found = true;
                String[] queryTitle = { "title", "air_date", "runtime", "rating" };
                String[] dataType = { "string", "string", "int", "double" };
                String[] header = { "Title", "Air Date", "Runtime", "Rating" };
                printTable(resultSet, header, dataType, queryTitle);
            }

            if (!found) {
                System.out.println("No records found.");
            }
            resultSet.close();
        } catch (SQLException e) {
            reportError(e);
        }
    }

    public void aPersonTopMovies(String name, int limit) {
        String sql = "SELECT TOP(" + limit
                + ") title,air_date,rating,role FROM Movie INNER JOIN workedIn ON Movie.imdb_id = workedIn.imdb_id INNER JOIN People ON workedIn.person_id = People.person_id WHERE name = ? ORDER BY rating DESC;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            boolean found = false;

            if (!isMyResultSetEmpty(resultSet)) {
                found = true;
                String[] queryTitle = { "title", "air_date", "role", "rating" };
                String[] dataType = { "string", "string", "string", "double" };
                String[] header = { "Title", "Air Date", "Role", "Rating" };
                printTable(resultSet, header, dataType, queryTitle);
            }

            if (!found) {
                System.out.println("No records found.");
            }
            resultSet.close();
        } catch (SQLException e) {
            reportError(e);
        }
    }

    public void TopPerformingActorsinBirthYear(String date1, int limit) {
        String sql = "SELECT TOP(" + limit
                + ") name,birthdate,deathdate, AVG(Movie.rating) AS average_rating FROM Movie INNER JOIN workedIn ON Movie.imdb_id = workedIn.imdb_id INNER JOIN People ON workedIn.person_id = People.person_id WHERE YEAR(birthdate) = ? GROUP BY name,birthdate,deathdate ORDER BY average_rating DESC;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, date1);
            ResultSet resultSet = statement.executeQuery();

            boolean found = false;

            if (!isMyResultSetEmpty(resultSet)) {
                found = true;
                String[] queryTitle = { "name", "birthdate", "deathdate", "average_rating" };
                String[] dataType = { "string", "string", "string", "double" };
                String[] header = { "Name", "Birth date", "Death date", "Average Rating" };
                printTable(resultSet, header, dataType, queryTitle);
            }

            if (!found) {
                System.out.println("No records found.");
            }
            resultSet.close();
        } catch (SQLException e) {
            reportError(e);
        }
    }

    public void TopMoviesForCountry(String country, int limit) {
        String sql = "SELECT DISTINCT TOP(" + limit
                + ") Movie.imdb_id,title,air_date,runtime,rating FROM Movie JOIN MovieCountries on MovieCountries.imdb_id=Movie.imdb_id JOIN MovieLanguages on MovieLanguages.imdb_id=Movie.imdb_id WHERE Country = ? ORDER BY rating DESC;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, country);
            ResultSet resultSet = statement.executeQuery();

            boolean found = false;

            if (!isMyResultSetEmpty(resultSet)) {
                found = true;
                String[] queryTitle = { "imdb_id", "title", "air_date", "runtime", "rating" };
                String[] dataType = { "int", "string", "string", "int", "double" };
                String[] header = { "IMDB ID", "Title", "Air Date", "Runtime", "Rating" };
                printTable(resultSet, header, dataType, queryTitle);
            }

            if (!found) {
                System.out.println("No records found.");
            }
            resultSet.close();
        } catch (SQLException e) {
            reportError(e);
        }
    }

    public void TopSpendingProdCompany(int limit) {
        String sql = "SELECT TOP (" + limit
                + ") ProductionCompany.company_id,company_name,SUM(budget) as total FROM ProductionCompany JOIN Finances ON Finances.company_id=ProductionCompany.company_id GROUP BY ProductionCompany.company_id,company_name ORDER BY total DESC;";

        try {
            ResultSet resultSet = statement.executeQuery(sql);

            boolean found = false;

            if (!isMyResultSetEmpty(resultSet)) {
                found = true;
                String[] queryTitle = { "company_name", "total" };
                String[] dataType = { "string", "double" };
                String[] header = { "Company", "Total($)" };
                printTable(resultSet, header, dataType, queryTitle);
            }

            if (!found) {
                System.out.println("No records found.");
            }
            resultSet.close();
        } catch (SQLException e) {
            reportError(e);
        }
    }

    public void ListAllGenre() {
        String selectSql = "SELECT DISTINCT genre FROM MOVIE JOIN MovieGenre ON Movie.imdb_id=MovieGenre.imdb_id ORDER BY genre;";

        try {
            ResultSet resultSet = statement.executeQuery(selectSql);

            boolean found = false;

            if (!isMyResultSetEmpty(resultSet)) {
                found = true;
                String[] queryTitle = { "genre" };
                String[] dataType = { "string" };
                String[] header = { "Genre" };
                printTable(resultSet, header, dataType, queryTitle);
            }

            if (!found) {
                System.out.println("No records found.");
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ListAllCountries() {
        String selectSql = "SELECT DISTINCT country FROM MOVIE JOIN MovieCountries ON Movie.imdb_id=MovieCountries.imdb_id ORDER BY country;";

        try {
            ResultSet resultSet = statement.executeQuery(selectSql);

            boolean found = false;

            if (!isMyResultSetEmpty(resultSet)) {
                found = true;
                String[] queryTitle = { "country" };
                String[] dataType = { "string" };
                String[] header = { "Country" };
                printTable(resultSet, header, dataType, queryTitle);
            }

            if (!found) {
                System.out.println("No records found.");
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}