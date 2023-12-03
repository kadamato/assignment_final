import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class BookStore {
    //  store book list
    ArrayList<Book> inventory = new ArrayList<Book>();

    ArrayList<Genre>  genresList = new ArrayList<>();

    ArrayList<Author> authorsList =  new ArrayList<>();

    ArrayList<Nationality>  nationalitiesList =  new ArrayList<>();

    ArrayList<Publisher>  publishersList = new ArrayList<>();
    String name;
    String createdDate;

    static int countBook = 0;

     Connection  connect;


    public BookStore (String name,String createdDate ) {
        this.name = name;
        this.createdDate =  createdDate;
    }


    //main features
    public void add(String authorName, String authorEmail, String authorPhone, String authorNationality, String authorDescription, String title, String genre, float sellingPrice, int pageNumber, String isbnCode, String pubYear, String publisher , int bookQuantity) {
        try {
            int checkBook = getBookId(title);
            int checkGenre = getGenreId(genre);
            int checkAuhtor = getAuthorId(authorName);
            int checkPublisher =  getPublisherId(publisher);



            // if not exist
            if(checkBook == 0 || checkGenre == 0 || checkAuhtor == 0 || checkPublisher == 0)  {
                // save

                int savePublisher = addPublisher(publisher);
                int saveAuthor = addAuthor(authorName, authorEmail, authorPhone, authorNationality , authorDescription);
                int saveGenre = addGenre(genre);
                int saveBook = addBook(title, sellingPrice, pageNumber, isbnCode, pubYear, savePublisher, bookQuantity);
                int nationalityId =   authorNationality.isEmpty() ? 101 : getNationalityId(authorNationality);

                boolean saveAuthorNationalities =  addAuthorNationalities(saveAuthor, nationalityId);
                boolean saveBookAuthor = addBookAuthor(saveBook, saveAuthor);
                boolean  saveBookGenre = addBookGenre(saveBook, saveGenre);

                if(saveAuthorNationalities && saveBookAuthor && saveBookGenre) {
                    System.out.println(Support.greenColor("Add a new book success"));
                }
            }
            else {
                System.out.println("");
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        finally {
            countBook +=1;
        }

    }


    public boolean addAuthorNationalities (int authorId , int nationalityId) {
        boolean  result  = false;
        try {
            String statement ="insert into  authorNationalities( author_id, nationality_id ) values(?,?)";

            PreparedStatement preparedInsertAuthor = connect.prepareStatement(statement);

            preparedInsertAuthor.setInt(1 , authorId );
            preparedInsertAuthor.setInt(2, nationalityId);

            int rowAffected = preparedInsertAuthor.executeUpdate();

            if(rowAffected > 0) {
                result = true;
            }
        }
        catch (SQLException  error) {
            error.printStackTrace();
        }
        finally {
            return result;
        }
    }

    public boolean addBookAuthor (int bookId , int authorId) {
        boolean  result  = false;
        try {
            String statement ="insert into  bookAuthor( book_id, author_id ) values(?,?)";

            PreparedStatement preparedInsertAuthor = connect.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);

            preparedInsertAuthor.setInt(1 , bookId );
            preparedInsertAuthor.setInt(2, authorId);

            int rowAffected = preparedInsertAuthor.executeUpdate();

            if(rowAffected > 0) {
                result = true;
            }
        }
        catch (SQLException  error) {
            error.printStackTrace();
        }
        finally {
            return result;
        }
    }

    public boolean addBookGenre (int bookId , int genreId) {
        boolean  result  = false;
        try {
            String statement ="insert into  bookGenre( book_id, genre_id ) values(?,?)";

            PreparedStatement preparedInsertAuthor = connect.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);

            preparedInsertAuthor.setInt(1 , bookId );
            preparedInsertAuthor.setInt(2, genreId);

            int rowAffected = preparedInsertAuthor.executeUpdate();

            if(rowAffected > 0) {
                result = true;
            }
        }
        catch (SQLException  error) {
            error.printStackTrace();
        }
        finally {
            return result;
        }

    }

    public int addAuthor (String authorName , String authorEmail , String authorPhone , String authorNationality , String authorDescription) {

       int  id  = 0;
        try {
            String statement ="insert into  authors( name, email , phone  , description ) values(?,?,?,?)";

            PreparedStatement preparedInsertAuthor = connect.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);

            preparedInsertAuthor.setString(1 , authorName );
            preparedInsertAuthor.setString(2, authorEmail);
            preparedInsertAuthor.setString(3, authorPhone);
            preparedInsertAuthor.setString(4, authorDescription);

            int rowAffected = preparedInsertAuthor.executeUpdate();

            if(rowAffected > 0) {
                ResultSet generatedKey = preparedInsertAuthor.getGeneratedKeys();
                if (generatedKey.next()) {
                    int authorId = generatedKey.getInt(1);
                    id = authorId;
                }
            }
        }
        catch (SQLException  error) {
            error.printStackTrace();
        }
        finally {
            return id;
        }


    }

    public int  getAuthorId (String authorName) {
        int id = 0;
        try {

            String query = "select * from authors  where  name = ?";

            PreparedStatement preparedStatement = connect.prepareStatement(query);

            preparedStatement.setString(1, authorName);

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                int authorId = result.getInt(1);
                id = authorId;
            }
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        finally {
            return id;
        }
    }


    public  int addGenre (String genre) {
        int id = 0;
        try {
            String query = "insert into genres(name) values (?)";
            PreparedStatement preparedStatement = connect.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, genre);

            int addGenre =  preparedStatement.executeUpdate();

            if(addGenre > 0 ) {
                ResultSet generateBookId = preparedStatement.getGeneratedKeys();
                if(generateBookId.next()) {
                    int bookId = generateBookId.getInt(1);
                    id = bookId;
                }
            }
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        finally {
            return id;
        }
    }
    public int  getPublisherId (String authorName) {
        int id = 0;
        try {

            String query = "select * from publishers  where  name = ?";

            PreparedStatement preparedStatement = connect.prepareStatement(query);

            preparedStatement.setString(1, authorName);

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                int publisherId = result.getInt(1);
                id = publisherId;
            }
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        finally {
            return id;
        }
    }

    public  int addPublisher (String publisherName) {
        int id = 0;
        try {
            String query = "insert into publishers(name) values (?)";
            PreparedStatement preparedStatement = connect.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, publisherName);

            int addPublisher =  preparedStatement.executeUpdate();

            if(addPublisher > 0 ) {
                ResultSet generatePublisherId = preparedStatement.getGeneratedKeys();
                if(generatePublisherId.next()) {
                    int publisherId = generatePublisherId.getInt(1);
                    id = publisherId;
                }
            }
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        finally {
             return id;
        }
    }

    public int getGenreId (String genreName) {
        int id= 0;

        try {
            String query =  "select * from genres where name  = ?";

            PreparedStatement  preparedStatement =  connect.prepareStatement(query);

            preparedStatement.setString(1, genreName);

            ResultSet  resultSet  = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int genreId = resultSet.getInt(1);
                id = genreId;
            }

        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        finally {
            return id;
        }
    }

    public int  addBook ( String title,float sellingPrice, int pageNumber, String isbnCode, String pubYear,int publisher_id, int quantity) {
        int id = 0;
        try {
            String addBookStatement = "insert into books(bookTitle, sellingPrice, pageNumber, isbnCode, publicationYear, publisher_id , quantity)" +
                    "values (?,?,?,?,?,?,?)";
            PreparedStatement  preparedInsertBook = connect.prepareStatement(addBookStatement,Statement.RETURN_GENERATED_KEYS);

            preparedInsertBook.setString(1, title);
            preparedInsertBook.setFloat(2, sellingPrice);
            preparedInsertBook.setInt(3, pageNumber);
            preparedInsertBook.setString(4, isbnCode);
            preparedInsertBook.setString(5, pubYear);
            preparedInsertBook.setInt(6,publisher_id );
            preparedInsertBook.setInt(7, quantity);
            int addBook =  preparedInsertBook.executeUpdate();


            if(addBook > 0) {
                ResultSet generateBookId = preparedInsertBook.getGeneratedKeys();
                if (generateBookId.next()) {
                    int bookId = generateBookId.getInt(1);
                    id = bookId;
                }
            }
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        finally {
            return id;
        }
    }

    public int getBookId (String bookName) {

        int id= 0;

        try {
            String query =  "select * from books where bookTitle  = ?";

            PreparedStatement  preparedStatement =  connect.prepareStatement(query);

            preparedStatement.setString(1, bookName);

            ResultSet  resultSet  = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int bookId = resultSet.getInt(1);
                id = bookId;
            }

        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        finally {
            return id;
        }
    }
    public void update (int bookId ,String title , String genre , float sellingPrice , int pageNumber , String isbnCode , String publicationYear , String publisher , String author) {
        try {
            String statement = "update books set  bookTitle = ?, genre = ?, sellingPrice = ?, pageNumber = ?, isbnCode = ?, publicationYear = ?, publisher = ? where id = ?";
            PreparedStatement preparedUpdateBook = connect.prepareStatement(statement ,Statement.RETURN_GENERATED_KEYS);


            preparedUpdateBook.setString(1, title);
            preparedUpdateBook.setString(2, genre);
            preparedUpdateBook.setFloat(3, sellingPrice);
            preparedUpdateBook.setInt(4, pageNumber);
            preparedUpdateBook.setString(5, isbnCode);
            preparedUpdateBook.setString(6, publicationYear);
            preparedUpdateBook.setString(7, publisher);
            preparedUpdateBook.setInt(8, bookId);


            int  updateBook = preparedUpdateBook.executeUpdate();

            if(updateBook > 0) {

                String retrieveAuthorId = "select author_id from books where id = ?";

                PreparedStatement preparedGetAuthorId = connect.prepareStatement(retrieveAuthorId);
                preparedGetAuthorId.setInt(1, bookId);
                ResultSet getAuthorId = preparedGetAuthorId.executeQuery();

                if(getAuthorId.next()) {
                    int authorId = getAuthorId.getInt("author_id");


                    String updateBookStatement = "update authors set name = ? where id = ?";
                    PreparedStatement preparedUpdateAuthor =  connect.prepareStatement(updateBookStatement);


                    preparedUpdateAuthor.setString(1, author);
                    preparedUpdateAuthor.setInt(2, authorId);


                    int updateAuthor = preparedUpdateAuthor.executeUpdate();

                    if(updateAuthor > 0) {
                        System.out.println("update book successfully");
                    }
                    else {
                        System.out.println("update author failed");
                    }
                }
            }
            else {
                System.out.println("update book failed");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean checkBook (String bookName) {
        boolean result = false;
        for(Book book : inventory) {
            System.out.println(book.bookTitle);
            if(Support.capitalizeText(book.bookTitle).equals(Support.capitalizeText(bookName))) {
                result = true;
            }
        }
        return result;
    }

    public void delete (int bookId) {
        try {

            Statement statement = connect.createStatement();
            connect.setAutoCommit(false);

            // Add multiple delete statements to the batch
            statement.addBatch("DELETE FROM publishers WHERE id = " + bookId);
            statement.addBatch("DELETE FROM books WHERE id = " + bookId);
            statement.addBatch("DELETE FROM genres WHERE id = " + bookId);
            statement.addBatch("DELETE FROM authors WHERE id = " + bookId);
            statement.addBatch("DELETE FROM bookGenre WHERE book_id =" + bookId);
            statement.addBatch("DELETE FROM bookAuthor WHERE book_id =" + bookId);
            statement.addBatch("DELETE FROM authorNationalities WHERE author_id = " + bookId);

            // Execute the batch
            int[] affectedRows = statement.executeBatch();

            // Commit the transaction
            connect.commit();

            // Enable auto-commit again
            connect.setAutoCommit(true);

            System.out.println("Delete book success");
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
    }

    // features users
    public void getBooks () {
        // reset inventory
        inventory = new ArrayList<>();
        try {

                String query = "select books.id as id , books.bookTitle as bookTitle , books.sellingPrice as sellingPrice , books.pageNumber as pageNumber, books.isbnCode as isbnCode , books.publicationYear as publicationYear , books.quantity as quantity ,\n" +
                        "        authors.name as authorName, authors.email as authorEmail , authors.phone as authorPhone , authors.description as authorDescription ,\n" +
                        "        nationalies.name as nationalityName ,\n" +
                        "        genres.name as genreName,\n" +
                        "        publishers.name as publisher\n" +
                        "from bookAuthor\n" +
                        "    inner join  books on  bookAuthor.book_id = books.id\n" +
                        "    inner join  authors on bookAuthor.author_id = authors.id\n" +
                        "    inner  join  bookGenre on books.id = bookGenre.book_id\n" +
                        "    inner  join  authorNationalities on authors.id = authorNationalities.author_id\n" +
                        "    inner  join  genres on bookGenre.genre_id = genres.id\n" +
                        "    inner  join  nationalies on authorNationalities.nationality_id = nationalies.id\n" +
                        "    inner  join  publishers on books.publisher_id = publishers.id;";

                Statement statement = connect.createStatement();

                ResultSet books = statement.executeQuery(query);

                while(books.next()) {
                    int id = books.getInt("id");
                    String bookTitle = books.getString("bookTitle");
                    float sellingPrice = books.getFloat("sellingPrice");
                    int pageNumber = books.getInt("pageNumber");
                    String isbnCode = books.getString("isbnCode");
                    Date publicationYear = books.getDate("publicationYear");
                    String publisher = books.getString("publisher");
                    String author = books.getString("authorName");
                    String email = books.getString("authorEmail");
                    String phone = books.getString("authorPhone");
                    String description = books.getString("authorDescription");
                    String nationality = books.getString("nationalityName");
                    String genre = books.getString("genreName");
                    int quantity = books.getInt("quantity");

                    Book newBook = new Book(id,bookTitle, sellingPrice,author, pageNumber, isbnCode, publicationYear, publisher, email, phone , description, nationality, genre, quantity);

                    inventory.add(newBook);
                }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public  void showBooks () {
        countBook = 0;
        for (Book book : inventory) {
            countBook += 1;
            System.out.println("STT: "+ countBook);
            System.out.println(book);
        }
        String message =
                "Result\n" + "\tTotal number of books available: " + countBook  ;
        String messageResult =  Support.boldText(Support.italicText(message));
        System.out.println(messageResult);
    }

    // feature others
    public  void sortAuthor (String content)  {
        System.out.println("----------------------all books sorted from high to low"+"----------------------\n");
        if(content.equals("a-z")) {
            countBook = 0;
            ArrayList<Book>  sortedBooks = Support.sortAuthor(inventory);

            for(Book book : sortedBooks) {
                countBook += 1;
                System.out.println("STT: "+  countBook);
                System.out.println(book);
            }

            String message =
                    "Result" + "\n\tTotal number of books available: " + countBook;
            String messageResult =  Support.boldText(Support.italicText(message));
            System.out.println(messageResult);
        }
    }

    public  void sortPrice (String content) {
        if(content.equals("a-z")) {
            countBook = 0;
            System.out.println("----------------------price of all books  sorted from high to low" + "----------------------\n");


            if (content.equals("z-a")) {

            } else {
                ArrayList<Book> sortedBooks = Support.sortPrice(inventory);

                for (Book book : sortedBooks) {
                    countBook+= 1;
                    System.out.println("STT: " + countBook);
                    System.out.println(book);
                }
                String message =
                        "Result" + "\n\tTotal number of books available: " + countBook;
                String messageResult =  Support.boldText(Support.italicText(message));
                System.out.println(messageResult);

            }
        }
    }

    public  void filterAuthorName (String authorName) {
        System.out.println("----------------------all books have contain word #" +  authorName +"----------------------\n");
        int resultCount = 0;
        for (Book book : inventory) {
            if(book.author.toLowerCase().contains(authorName.toLowerCase())) {
                resultCount += 1;
                System.out.println("STT: " + resultCount);
                System.out.println(book);
            }
        }

        String resultMessage = "";
        if(resultCount < 1) {
            String message =  "Result\n" + "\tNo books found author name  in the #" + authorName;
            resultMessage = Support.boldText(Support.italicText(message));
        }
        else {
            String  message ="Result\n" +  "\tKeyword: " + authorName +  "\n\tTotal number of results found: "+  resultCount ;
            resultMessage = Support.boldText(Support.italicText(message));
        }

        System.out.println(resultMessage);
    }

    public  void findBook (String bookName) {
        ArrayList<Book> results  = new ArrayList<>();

        int resultCount = 0;
        for (Book book : inventory) {
            if(book.bookTitle.toLowerCase().contains(bookName.toLowerCase())) {
                results.add(book);
            }
        }

        for (Book book : results) {
            resultCount += 1;
            System.out.println("STT: " + resultCount);
            System.out.println(book);
        }


        String resultMessage = "";
        if(resultCount < 1) {
            String message = "Result\n" + "\t No find any book have title #" +bookName;
            resultMessage = Support.boldText(Support.italicText(message));
        }
        else  {
            String  message ="Result\n" +  "\tKeyword: " + bookName +  "\n\tTotal number of results found: "+  resultCount ;
            resultMessage = Support.boldText(Support.italicText(message));
        }

        System.out.println(resultMessage);
    }

    /**
     * @param genre of a book
     * @return a list book have specify genre
     */
    public void filterFollowGenre (String genre) {
        System.out.println("----------------------" + "All books have the #" + genre + " genre" + "----------------------\n");
        int resultCount = 0;
        for (Book book : inventory) {
            if(book.genre.toLowerCase().contains(genre.toLowerCase())) {
                System.out.println(book);
                System.out.println("STT: ");
                resultCount += 1;
            }
        }

        String resultMessage = "";

        if(resultCount < 1) {
           String message  = "Result\n" + "\tNo books found in the #" + genre + " genre";
           resultMessage = Support.boldText(Support.italicText(message));
        }
        else {
            String  message ="Result\n" +  "\tKeyword: " + genre +  "\n\tTotal number of results found: "+  resultCount;
            resultMessage = Support.boldText(Support.italicText(message));
        }
        System.out.println(resultMessage);
    }


    public void  getGenres () {
        try {
            String query = "select * from genres";

            Statement statement = connect.createStatement();

            ResultSet genres = statement.executeQuery(query);

            int genreCount = 0;
            while (genres.next()) {
                genreCount += 1;
                String name =  genres.getString("name");
                int id = genres.getInt("id");
                Genre newGenre =  new Genre(genreCount , id , name);
                genresList.add(newGenre);
            }
        }
        catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public void showGenres () {
        for (Genre genre: genresList) {
            System.out.println(genre.stt  + ". " + genre.name);
        }
    }

    public String  findGenre (int stt) {
        String genreName = "";
        for(Genre genre: genresList) {
            if(stt ==  genre.stt) {
               genreName = genre.name;
               break;
            }
        }
        return genreName;
    }

    public boolean checkGenre (String genreName) {
        boolean result = false;
        for (Genre genre: genresList) {
            if(Support.capitalizeText(genre.name).equals(Support.capitalizeText(genreName))) {
                result = true;
            }
        }
        return result;
    }


    public void getAuhors () {
        try {
            String query = "select authors.id as id , authors.name as name , authors.email as email , authors.phone as phone , authors.description as description , nationalies.name as nationality   from authorNationalities inner join  authors  on authorNationalities.author_id = authors.id inner  join  nationalies on authorNationalities.nationality_id = nationalies.id\n";
            Statement statement = connect.createStatement();

            ResultSet authors =  statement.executeQuery(query);

            int count = 0;
            while (authors.next()) {
                count += 1;
                int id = authors.getInt("id");
                String name = authors.getString("name");
                String email = authors.getString("email");
                String phone = authors.getString("phone");
                String nationality = authors.getString("nationality");
                String description = authors.getString("description");


                Author newAuthor = new Author(count ,id , name , email, phone, nationality, description);
                authorsList.add(newAuthor);
            }
        }
        catch (SQLException error) {
            error.printStackTrace();
        }


    }

    public void showAuthors () {
        for (Author author: authorsList) {
            System.out.println(author.stt  + ". " + author.name);
        }
    }

    public String findAuthors (int stt) {
        String authorName = "";
        for( Author author : authorsList) {
            if(author.stt ==  stt) {
                authorName = author.name;
                break;
            }
        }

        return authorName;
    }

    public boolean checkAuthor (String authorName) {
        boolean result = false;
        for(Author author : authorsList) {
            if(Support.capitalizeText(author.name).equals(Support.capitalizeText(authorName))) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean checkAuthor (String authorName , String authorEmail) {
        boolean result = false;
        for(Author author : authorsList) {
             if(Support.capitalizeText(author.name).equals(Support.capitalizeText(authorName))) {
                 if(Support.capitalizeText(author.email).equals(Support.capitalizeText(authorEmail))) {
                     result = true;
                     break;
                 }

             }
        }
        return result;
    }

    public boolean checkAuthor (String authorName , String authorEmail , String authorPhone) {
        boolean result = false;
        for(Author author : authorsList) {
            if(!Support.capitalizeText(author.name).equals(Support.capitalizeText(authorName))) continue;
            if(!Support.capitalizeText(author.email).equals(Support.capitalizeText(authorEmail)))  continue;

            if(Support.capitalizeText(author.phone).equals(Support.capitalizeText(authorPhone))) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean checkAuthor (String authorName , String authorEmail , String authorPhone, String authorNationality) {
        boolean result = false;
        for(Author author : authorsList) {
            if(!Support.capitalizeText(author.name).equals(Support.capitalizeText(authorName))) continue;
            if(!Support.capitalizeText(author.email).equals(Support.capitalizeText(authorEmail)))  continue;
            if(!Support.capitalizeText(author.phone).equals(Support.capitalizeText(authorPhone))) continue;

            if(Support.capitalizeText(author.nationality).equals(Support.capitalizeText(authorNationality))) {
                result = true;
                break;
            };
        }
        return result;
    }

    public boolean checkAuthor (String authorName , String authorEmail , String authorPhone, String authorNationality, String authorDescription) {
        boolean result = false;
        for(Author author : authorsList) {
            if(!Support.capitalizeText(author.name).equals(Support.capitalizeText(authorName))) continue;
            if(!Support.capitalizeText(author.email).equals(Support.capitalizeText(authorEmail)))  continue;
            if(!Support.capitalizeText(author.phone).equals(Support.capitalizeText(authorPhone))) continue;

            if(!Support.capitalizeText(author.nationality).equals(Support.capitalizeText(authorNationality))) continue;


            if(Support.capitalizeText(author.description).equals(Support.capitalizeText(authorDescription))) {
                result = true;
                break;
            }


        }
        return result;
    }

    public void getNationalities () {
        try {

            String query = "select * from nationalies";

            Statement statement =  connect.createStatement();

            ResultSet resultSet =  statement.executeQuery(query);

            int count = 0;
            while (resultSet.next()) {
                count += 1;
                int  nationalityId = resultSet.getInt("id");
                String nationalityName = resultSet.getString("name");
                Nationality newNationality  = new Nationality(count ,nationalityId,nationalityName );
                nationalitiesList.add(newNationality);

            }


        }
        catch (SQLException error) {

        }
    }

    public void showNationalities () {
        for (Nationality nationality: nationalitiesList) {
            System.out.println(nationality.stt  + ". " + nationality.name);
        }
    }

    public String  findNationalities (int stt) {
        String name = "";
        for(Nationality nationality: nationalitiesList) {
            if(nationality.stt == stt) {
                name = nationality.name;
            }
        }
        return name;

    }

    public int getNationalityId (String nationalityName ) {
        int id= 0;

        try {
            String query =  "select * from nationalies where name  = ?";

            PreparedStatement  preparedStatement =  connect.prepareStatement(query);

            preparedStatement.setString(1, nationalityName);

            ResultSet  resultSet  = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int nationalityId = resultSet.getInt(1);
                id = nationalityId;
            }
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
        finally {
            return id;
        }


    }



    public void getPublishers () {
        try {
            String query = "select * from publishers";
            Statement statement = connect.createStatement();

            ResultSet publishers =  statement.executeQuery(query);

            int count = 0;
            while (publishers.next()) {
                count += 1;
                int id = publishers.getInt("id");
                String name = publishers.getString("name");


                Publisher newPublisher = new Publisher(count , id , name);
                publishersList.add(newPublisher);
            }
        }
        catch (SQLException error) {
            error.printStackTrace();
        }
    }

    public void showPublishers () {
        for (Publisher publisher: publishersList) {
            System.out.println(publisher.stt  + ". " + publisher.name);
        }
    }

    public String findPublisher (int stt) {
        String publisherName = "";
        for( Publisher publisher : publishersList) {
            if(publisher.stt ==  stt) {
                publisherName = publisher.name;
                break;
            }
        }

        return publisherName;
    }

    public boolean checkPublisher (String publisherName) {
        boolean result = false;
        for(Publisher publisher : publishersList) {
            if(Support.capitalizeText(publisher.name).equals(Support.capitalizeText(publisherName))) {
                result = true;
                break;
            }
        }
        return result;
    }





    public  boolean connectDB ()  {
        connect = Mysql.connect("jdbc:mysql://localhost:3306/book_store", "kadamato", "123abc");
        if(connect != null) return true;
        return false;
    }

    public  String toString() {
        return "Store name: " + this.name  + "\n" + "Created: " + this.createdDate + "\n" + "Count book: " + countBook;
    }
    public static void main (String[] args )  {

    }



}


