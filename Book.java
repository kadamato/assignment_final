import java.util.Date;

class Book {

    int id;
    String isbnCode;
    String bookTitle;
    String author;
    Date publicationYear;
    String genre;
    String publisher;
    float sellingPrice;
    int pageNumber;

    public  Book (int id , String bookTitle , String genre, float sellingPrice , String author, int pageNumber ,String isbnCode , Date publicationYear , String publisher) {
        this.id = id;
        this.bookTitle = bookTitle.trim();
        this.genre = genre.trim();
        this.sellingPrice = sellingPrice;
        this.author = author.trim();
        this.pageNumber =  pageNumber;
        this.isbnCode=  isbnCode.trim();
        this.publicationYear = publicationYear;
        this.publisher = publisher.trim();
    }

    public  String toString () {
        return "@" + this.bookTitle + "\n" +
                "\tid:" + this.id + "\n" +
                "\tbookTitle:" + this.bookTitle +  "\n"+
                "\tgenre:" + this.genre + "\n"+
                "\tsellingPrice:" + this.sellingPrice + "\n"+
                "\tauthor:" + this.author + "\n"+
                "\tpageNumber:" + this.pageNumber + "\n"+
                "\tisbnCode:" + this.isbnCode + "\n"+
                "\tpublicationYear:" + this.publicationYear + "\n"+
                "\tpublisher:" + this.publisher + "\n";
    }

    public static void main (String[] args) {

    }
}
