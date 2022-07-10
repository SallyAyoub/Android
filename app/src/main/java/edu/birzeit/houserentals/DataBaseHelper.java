package edu.birzeit.houserentals;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;


import java.util.ArrayList;



public class DataBaseHelper extends SQLiteOpenHelper {
    public static int reservationID = 0;
  public static int applicationID = 0;
    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    } // creating a constructor for our database handler.
    //    public DataBaseHelper(Context context) {
//        super(context,"HomeRent", null, 1);
//    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(email VARCHAR(60) NOT NULL, firstName VARCHAR(45) , " +
                "lastName VARCHAR(45) , gender VARCHAR(10) , password VARCHAR(500) NOT NULL, " +
                "country VARCHAR(45) NOT NULL, city VARCHAR(45) NOT NULL, phoneNumber VARCHAR(45) NOT NULL,nationality VARCHAR(45)," +
                "salary LONG,occupation VARCHAR(45),familySize LONG ,agencyName VARCHAR(60)," +
                " PRIMARY KEY (email))");
        db.execSQL("CREATE TABLE properties(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, city VARCHAR(45) NOT NULL, postalAddress VARCHAR(45) NOT NULL," +
                "SurfaceArea LONG NOT NULL, numberOfBedrooms INT NOT NULL, rentalPrice LONG NOT NULL, " +
                " status varchar(35) NOT NULL, balcony varchar(30) NOT NULL, garden varchar(30) NOT NULL)");


        db.execSQL("CREATE TABLE reservations(idReservations INT NOT NULL, userEmail VARCHAR(45) NOT NULL, " +
                "propertyID INTEGER NOT NULL, firstName VARCHAR (20),lastName VARCHAR (20),reservedAt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, Seen varchar(40), PRIMARY KEY (idReservations), CONSTRAINT Email FOREIGN KEY (userEmail) REFERENCES " +
                "users(email) ON DELETE CASCADE ON UPDATE CASCADE, CONSTRAINT property FOREIGN KEY (propertyID) REFERENCES " +
                "properties(ID) ON DELETE CASCADE ON UPDATE CASCADE)");


        db.execSQL("CREATE TABLE posts(idposts INTEGER NOT NULL  PRIMARY KEY  AUTOINCREMENT, userEmail VARCHAR(45) NOT NULL, " +
                "propertyID INTEGER NOT NULL ,agencyName VARCHAR (20),postedAt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,constructionYear int NOT NULL,availablityDate varchar(50),description varchar(2000" +
                "),PhotoLink varchar(2000) NOT NULL, CONSTRAINT Email FOREIGN KEY (userEmail) REFERENCES " +
                "users(email) ON DELETE CASCADE ON UPDATE CASCADE, CONSTRAINT property FOREIGN KEY (propertyID) REFERENCES " +
                "properties(ID) ON DELETE CASCADE ON UPDATE CASCADE)");


     db.execSQL("CREATE TABLE applications(idApplication INTEGER NOT NULL  PRIMARY KEY  AUTOINCREMENT, userEmail VARCHAR(45) NOT NULL, " +
             "propertyID INTEGER NOT NULL ,firstName VARCHAR (40) NOT NULL,lastName VARCHAR (40) NOT NULL,agency varchar(45) NOT NULL,CONSTRAINT Email FOREIGN KEY (userEmail) REFERENCES " +
             "users(email) ON DELETE CASCADE ON UPDATE CASCADE, CONSTRAINT property FOREIGN KEY (propertyID) REFERENCES " +
             "properties(ID) ON DELETE CASCADE ON UPDATE CASCADE)");

        db.execSQL("CREATE TABLE rejections(idRejection INTEGER NOT NULL  PRIMARY KEY  AUTOINCREMENT, userEmail VARCHAR(45) NOT NULL, " +
                "propertyID INTEGER NOT NULL ,firstName VARCHAR (40) NOT NULL,lastName VARCHAR (40) NOT NULL,agency varchar(45) NOT NULL,Seen varchar(40),CONSTRAINT Email FOREIGN KEY (userEmail) REFERENCES " +
                "users(email) ON DELETE CASCADE ON UPDATE CASCADE, CONSTRAINT property FOREIGN KEY (propertyID) REFERENCES " +
                "properties(ID) ON DELETE CASCADE ON UPDATE CASCADE)");

}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("email", user.getEmail());
        content.put("firstName", user.getFirstName());
        content.put("lastName", user.getLastName());
        content.put("gender", user.getGender());
        content.put("password", user.getPassword());
        content.put("country", user.getCountry());
        content.put("city", user.getCity());
        content.put("phoneNumber", user.getPhone());
        content.put("nationality", user.getNationality());
        content.put("salary", user.getSalary());
        content.put("occupation", user.getOccupation());
        content.put("familySize", user.getFamilySize());
        content.put("agencyName", user.getAgencyName());

        db.insert("users", null, content);
    }


    public Cursor getUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM users WHERE email = ?", new String[] {email});
    }
    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("firstName", user.getFirstName());
        content.put("lastName", user.getLastName());
        content.put("password", user.getPassword());
        content.put("country", user.getCountry());
        content.put("city", user.getCity());
        content.put("phoneNumber", user.getPhone());
        content.put("nationality", user.getNationality());
        content.put("salary", user.getSalary());
        content.put("occupation", user.getOccupation());
        content.put("familySize", user.getFamilySize());
        content.put("agencyName", user.getAgencyName());

        db.update("users", content, "email = ?", new String[]{user.getEmail()});
    }



    public void insertProperty(Property property){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("CITY", property.getCity());
        content.put("postalAddress", property.getPostalAddress());
        content.put("SURFACEAREA", property.getSurfaceArea());
        content.put("NUMBEROFBEDROOMS", property.getNumberOfBedrooms());
        content.put("RENTALPRICE", property.getRentalPrice());
        content.put("STATUS", property.getStatus());
        content.put("BALCONY", property.getBalcony());
        content.put("GARDEN", property.getGarden());
        db.insert("properties", null, content);
    }

    public Cursor getProperty(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from properties where id = ?", new String[] {String.valueOf(id)});
    }


    public void updateProperty(Property property,int id){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("CITY", property.getCity());
        content.put("postalAddress", property.getPostalAddress());
        content.put("SURFACEAREA", property.getSurfaceArea());
        content.put("NUMBEROFBEDROOMS", property.getNumberOfBedrooms());
        content.put("RENTALPRICE", property.getRentalPrice());
        content.put("STATUS", property.getStatus());
        content.put("BALCONY", property.getBalcony());
        content.put("GARDEN", property.getGarden());
        db.update("PROPERTIES",content,"ID =?",new String[]{String.valueOf(id)});
    }

    public int removeProperty(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete("properties", "ID = ?", new String[] {String.valueOf(id)});
    }


    public Cursor propertyPostID(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT propertyID FROM posts WHERE idposts = ?", new String[] {String.valueOf(id)});
    }

    public Cursor getProperties(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM properties ",null);
    }

    public void insertReservation(int proID , String userEmail,String fName,String lName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("idReservations", reservationID++);
        content.put("userEmail", userEmail);
        content.put("propertyID",proID);
        content.put("firstName",fName);
        content.put("lastName",lName);
        content.put("Seen","false");
        db.insert("reservations", null, content);
    }
    public  void updateReservations(String Email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("Seen","true");
        db.update("reservations", content, "userEmail = ?", new String[]{Email});
    }


    //for tenant history
    public Cursor getReservations(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT properties.ID, properties.city, properties.postalAddress, posts.agencyname,reservations.reservedAt FROM properties INNER JOIN reservations on properties.ID = reservations.propertyID INNER JOIN posts on posts.propertyID=reservations.propertyID WHERE  reservations.userEmail = ?", new String[] {email});
    }
    public Cursor getReservationsOfTenant(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from reservations where userEmail=?",new String[]{email});
    }

    public Cursor getReservationsID(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from reservations where reservations.propertyID =?",new String[]{String.valueOf(id)});
    }
    public Cursor checkReserved(int proID){
        SQLiteDatabase db = this.getReadableDatabase();
        String id=String.valueOf(proID);
        Cursor cursor = db.rawQuery("SELECT *  FROM reservations WHERE reservations.propertyID=?",new String[]{id});
        return cursor;
    }

    public void insertPosts(int proID, String userEmail, String description, int year, String available, String agnName,String photourl){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("userEmail", userEmail);
        content.put("propertyID",proID);
        content.put("description",description);
        content.put("constructionYear",year);
        content.put("availablityDate",available);
        content.put("agencyName",agnName);
        content.put("PhotoLink",photourl);
        db.insert("posts", null, content);

    }
    public Cursor getPost(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from posts where idposts = ?", new String[] {String.valueOf(id)});
    }

    public Cursor getPostsbyID(int id ) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from posts where propertyID=?", new String[]{String.valueOf(id)});
    }


    //for agency history
    public Cursor getPostsby(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        //return db.rawQuery("SELECT properties.ID, properties.city, properties.postalAddress, reservations.reservedAt FROM properties INNER JOIN reservations on properties.ID = reservations.proID INNER JOIN users on users.email = reservations.userEmail WHERE users.email = ?", new String[] {email});
        return db.rawQuery("SELECT properties.ID, properties.city, properties.postalAddress, reservations.firstName,reservations.lastName,reservations.reservedAt FROM properties INNER JOIN reservations on properties.ID = reservations.propertyID INNER JOIN posts on posts.propertyID=reservations.propertyID  INNER JOIN users on users.email = posts.userEmail WHERE users.email = ?", new String[] {email});
    }

    public Cursor getPosts(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM posts ",null);
    }


    public Cursor getAllPosts(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM posts WHERE userEmail=? ",new String[]{email});
    }


    public Cursor getRecentPosts(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from posts ORDER BY postedAt ASC LIMIT 5 ",null);
    }

    public void updatePosts(int id,int proID, String userEmail, String description, int year, String available, String agnName,String photoURL){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("userEmail", userEmail);
        content.put("propertyID",proID);
        content.put("description",description);
        content.put("constructionYear",year);
        content.put("availablityDate",available);
        content.put("agencyName",agnName);
        content.put("PhotoLink",photoURL);
        db.update("posts",content,"idposts =?",new String[]{String.valueOf(id)});
    }
    public int removePost(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete("posts", "idposts = ?", new String[] {String.valueOf(id)});
    }

    public void insertApplication(int id,String Email,String fname,String lname,String agency){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("userEmail", Email);
        content.put("propertyID", id);
        content.put("firstName", fname);
        content.put("lastName", lname);
        content.put("agency", agency);
        db.insert("applications", null, content);
    }



    public int removeApplication (int id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete("applications", "idApplication = ?", new String[] {String.valueOf(id)});
    }

    public Cursor getApplicationsByAgency(String agency){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from applications where agency = ?", new String[] {agency});

    }


    public void insertRejection(int id,String Email,String fname,String lname,String agency){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("userEmail", Email);
        content.put("propertyID", id);
        content.put("firstName", fname);
        content.put("lastName", lname);
        content.put("agency", agency);
        content.put("Seen","false");
        db.insert("rejections", null, content);
    }

    public Cursor getRejectionsOfTenant(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from rejections where userEmail=?",new String[]{email});
    }
    public  void updateRejections(String Email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("Seen","true");
        db.update("rejections", content, "userEmail = ?", new String[]{Email});


    }

    public Cursor getMaxIDRproperties(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT *  FROM properties  ORDER BY ID DESC LIMIT 1 ",null);
    }


    public Cursor getMaxIDReservations(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM reservations ORDER BY idReservations DESC LIMIT 1",null);
    }


    public Cursor getArea(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select distinct SurfaceArea from properties order by SurfaceArea", null);
    }
    public Cursor getPrices() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select distinct rentalPrice from properties order by rentalPrice", null);
    }
    public Cursor getRooms() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select distinct numberOfBedrooms from properties order by numberOfBedrooms", null);
    }


    public Cursor getMinMaxPrice(int flag) {
        SQLiteDatabase db = this.getReadableDatabase();
        if(flag == 0)
            return db.rawQuery("Select min(rentalPrice) from properties", null);
        else
            return db.rawQuery("Select max(rentalPrice) from properties", null);
    }
    public Cursor getMinMaxArea(int flag) {
        SQLiteDatabase db = this.getReadableDatabase();
        if(flag == 0)
            return db.rawQuery("Select min(SurfaceArea) from properties", null);
        else
            return db.rawQuery("Select max(SurfaceArea) from properties", null);
    }

    public Cursor getMinMaxRooms(int flag) {
        SQLiteDatabase db = this.getReadableDatabase();
        if(flag == 0)
            return db.rawQuery("Select min(numberOfBedrooms) from properties", null);
        else
            return db.rawQuery("Select max(numberOfBedrooms) from properties", null);
    }

    public Cursor getFilteredProperty(String minPrice,String maxPrice, String minArea, String maxArea,String minRooms, String maxRooms,String city,String status,String garden,String balcony) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("Select * from properties where  (rentalPrice between ? and ?) and (SurfaceArea between ? and ?) and (numberOfBedrooms between ? and ?) and city=? and status=? and garden=? and balcony=? ", new String[]{minPrice, maxPrice, minArea, maxArea, minRooms, maxRooms, city, status, garden, balcony});
    }
    
}