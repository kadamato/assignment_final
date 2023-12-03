import java.util.Date;

class Book {

    int id;
    String isbnCode,email,phone,description,nationality;
    String bookTitle;
    String author;
    Date publicationYear;
    String genre;
    String publisher;
    float sellingPrice;
    int pageNumber, quantity;

    public  Book (int id , String bookTitle , float sellingPrice , String author, int pageNumber ,String isbnCode , Date publicationYear , String publisher, String email , String phone , String description , String nationality, String genre, int quantity) {
        this.id = id;
        this.bookTitle = bookTitle.trim();
        this.sellingPrice = sellingPrice;
        this.author = author.trim();
        this.pageNumber =  pageNumber;
        this.isbnCode=  isbnCode.trim();
        this.publicationYear = publicationYear;
        this.publisher = publisher.trim();
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.nationality = nationality;
        this.genre = genre;
        this.quantity = quantity;
    }

    public  String toString () {
        return  "\tId: " + this.id + "\n" +
                "\tBookTitle: " + this.bookTitle +  "\n"+
                "\tSellingPrice: " + this.sellingPrice + "$" + "\n"+
                "\tAuthor: " + this.author + "\n"+
                "\tPageNumber: " + this.pageNumber + "\n"+
                "\tISBNCode: " + this.isbnCode + "\n"+
                "\tPublicationYear: " + this.publicationYear + "\n"+
                "\tPublisher: " + this.publisher + "\n" +
                "\tEmail: " + this.email + "\n" +
                "\tPhone: " + this.phone + "\n" +
                "\tQuantity: " + this.quantity + "\n" +
                "\tDescription: " + this.description + "\n" +
                "\tNationality: " + this.nationality + "\n" +
                "\tGenre: " + this.genre + "\n" ;
    }

    public static void main (String[] args) {

    }
}
