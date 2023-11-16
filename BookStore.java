import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class BookStore {
    //  store book list
    static ArrayList<Book> inventory = new ArrayList<Book>();
    String name;
    String createdDate;

    static int countBook = 0;
    static Connection  connect;


    public BookStore (String name,String createdDate ) {
        this.name = name;
        this.createdDate =  createdDate;
    }


    //main features
    public void  add (String authorName,String title , String genre , float sellingPrice , int pageNumber , String isbnCode , String publicationYear , String publisher) {
        try {

            String statement ="insert into  authors( name) values(?)";

            PreparedStatement preparedInsertAuthor = connect.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);

            preparedInsertAuthor.setString(1 , authorName );

            int rowAffected = preparedInsertAuthor.executeUpdate();

            if(rowAffected > 0) {
                ResultSet generatedKey = preparedInsertAuthor.getGeneratedKeys();
                if(generatedKey.next()) {
                   int authorId = generatedKey.getInt(1);

                    String addBookStatement = "insert into books(bookTitle, genre, sellingPrice, pageNumber, isbnCode, publicationYear, publisher, author_id)" +
                            "values (?,?,?,?,?,?,?,?)";
                    PreparedStatement  preparedInsertBook = connect.prepareStatement(addBookStatement);

                    preparedInsertBook.setString(1, title);
                    preparedInsertBook.setString(2, genre);
                    preparedInsertBook.setFloat(3, sellingPrice);
                    preparedInsertBook.setInt(4, pageNumber);
                    preparedInsertBook.setString(5, isbnCode);
                    preparedInsertBook.setString(6, publicationYear);
                    preparedInsertBook.setString(7, publisher);
                    preparedInsertBook.setInt(8, authorId);

                    int addBook =  preparedInsertBook.executeUpdate();

                    if(addBook > 0) {
                        System.out.println("add book successfully");
                    }
                    else {
                        System.out.println("add book failed");
                    }
                }
            }
            else {
                System.out.println("add author failed");
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            countBook +=1;
        }
    }


    public void update (
            int bookId ,String title , String genre , float sellingPrice , int pageNumber , String isbnCode , String publicationYear , String publisher , String author
    ) {
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

    public void delete (int bookId) {
        try {
            //get authorId
            String findAuthorIdStatement  = "select author_id from books where id = ?";

            PreparedStatement preparedFindAuthorId =  connect.prepareStatement(findAuthorIdStatement, Statement.RETURN_GENERATED_KEYS);

            preparedFindAuthorId.setInt(1, bookId);

            ResultSet  resultSet =  preparedFindAuthorId.executeQuery();


            // delete specify book
            String statement =  "delete from books where id = ?";
            PreparedStatement preparedDeleteBook = connect.prepareStatement(statement);

            preparedDeleteBook.setInt(1, bookId);

            int deleteBook = preparedDeleteBook.executeUpdate();
            if(resultSet.next() && deleteBook > 0) {
               int authorId = resultSet.getInt("author_id");


                String deleteAuthorStatement = "delete from authors where id = ?";
                PreparedStatement preparedDeleteAuthor = connect.prepareStatement(deleteAuthorStatement);
                preparedDeleteAuthor.setInt(1, authorId);


                int deleteAuthor = preparedDeleteAuthor.executeUpdate();
                if(deleteAuthor > 0) {
                    System.out.println("delete book successfully");
                }
                else {
                    System.out.println("delete author failed");
                }
            }
            else {
                System.out.println("delete book failed");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            countBook -=1;
        }
    }

    // features users
    public void getAll () {
        System.out.println("----------------Books available in store----------------");
        try {
                String query = "select *  ,(select name from authors where  books.author_id = authors.id) as author_name from books";

                Statement statement = connect.createStatement();

                ResultSet books = statement.executeQuery(query);

                while(books.next()) {
                    int id = books.getInt("id");
                    String bookTitle = books.getString("bookTitle");
                    String genre = books.getString("genre");
                    float sellingPrice = books.getFloat("sellingPrice");
                    int pageNumber = books.getInt("pageNumber");
                    String isbnCode = books.getString("isbnCode");
                    Date publicationYear = books.getDate("publicationYear");
                    String publisher = books.getString("publisher");
                    String author = books.getString("author_name");

                    Book newBook = new Book(id,bookTitle, genre, sellingPrice,author, pageNumber, isbnCode, publicationYear, publisher);

                    inventory.add(newBook);

                    countBook += 1;
                }


                for (Book book : inventory) {
                    System.out.println(book);
                }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    // feature others
    public static void sortAuthor (String content)  {
        if(!content.equals("a-z") && !content.equals("z-a")) {
            throw  new Error("argument specified wrong");
        }
        System.out.println("----------------------all books sorted from high to low"+"----------------------");
        if(content.equals("a-z")) {
            ArrayList<Book>  sortedBooks = Sort.sortAuthor(inventory);

            for(Book book : sortedBooks) {
                System.out.println(book);
            }
        }
    }

    public static void sortPrice (String content) {
        if(!content.equals("a-z") && !content.equals("z-a")) {
            throw  new Error("argument specified wrong ");
        }
        System.out.println("----------------------price of all books  sorted from high to low"+"----------------------");


        if(content.equals("z-a")) {

        }
        else {
            ArrayList<Book> sortedBooks = Sort.sortPrice(inventory);

            for (Book book : sortedBooks) {
                System.out.println(book);
            }
        }
    }

    public static void filterAuthorName (String authorName) {
        System.out.println("----------------------all books have contain word #" +  authorName +"----------------------");
        int resultCount = 0;
        for (Book book : inventory) {
            if(book.author.toLowerCase().contains(authorName.toLowerCase())) {
                System.out.println(book);
                resultCount += 1;
            }
        }

        if(resultCount < 1) {
            System.out.println("No have any results matches with word #" +authorName);
        }
    }

    public static void findBook (String bookName) {
        System.out.println("----------------------all books have title #" +  bookName +"----------------------");

        int resultCount = 0;
        for (Book book : inventory) {
            if(book.bookTitle.toLowerCase().contains(bookName.toLowerCase())) {
                System.out.println(book);
                resultCount += 1;
            }
        }

        if(resultCount < 1) {
            System.out.println("No find any book have name #" +bookName);
        }
    }

    /**
     * @param genre of a book
     * @return a list book have specify genre
     */
    public static  void filterFollowGenre (String genre) {
        System.out.println("----------------------all books of " +  genre + " type" + "----------------------");
        int resultCount = 0;
        for (Book book : inventory) {
            if(book.genre.toLowerCase().contains(genre.toLowerCase())) {
                System.out.println(book);
                resultCount += 1;
            }
        }
        if(resultCount < 1) {
            System.out.println("No find any book have genre #" + genre);
        }
    }




    public static boolean connectDB () {
        connect = Mysql.connect("jdbc:mysql://localhost:3306/book_store", "kadamato", "123abc");
        if(connect != null) return true;
        return false;
    }

    public  String toString() {
        return "Store name: " + this.name  + "\n" + "created: " + this.createdDate + "\n" + "count book: " + countBook;
    }



    public static void main (String[] args ) throws SQLException, SQLException {
    }
}


