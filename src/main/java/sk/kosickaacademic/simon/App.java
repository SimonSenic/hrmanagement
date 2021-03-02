package sk.kosickaacademic.simon;

import sk.kosickaacademic.simon.database.Database;

public class App 
{
    public static void main( String[] args )
    {
        Database db = new Database();
        db.getConnection();
    }
}
