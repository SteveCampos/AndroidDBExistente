package com.rupture.jairsteve.dbexistente;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by JairSteve on 30/11/2014.
 */
 public class DBHelper extends SQLiteOpenHelper {

    //Ruta por defecto de las bases de datos en el sistema Android
    private static String DB_PATH = "/data/data/com.rupture.jairsteve.dbexistente/databases/";

    private static String DB_NAME = "scan.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;
    private Cursor cursor;

    /**
     * Constructor
     * Toma referencia hacia el contexto de la aplicación que lo invoca para poder acceder a los 'assets' y 'resources' de la aplicación.
     * Crea un objeto DBOpenHelper que nos permitirá controlar la apertura de la base de datos.
     * @param context
     */
    public DBHelper(Context context) {

        super(context, DB_NAME, null, Constants.DATABASE_VERSION);
        this.myContext = context;

    }

    /**
     * Crea una base de datos vacía en el sistema y la reescribe con nuestro fichero de base de datos.
     * */
    public void createDataBase() throws IOException{


        boolean dbExist = checkDataBase();
        Log.d("DB", "checkea si la DB EXISTE y es "+dbExist);

        if(dbExist){
//la base de datos existe y no hacemos nada.
            Log.d("DB", "LA BASE DE DATOS EXISTE Y NO HACEMOS NADA");
        }else{
//Llamando a este método se crea la base de datos vacía en la ruta por defecto del sistema
//de nuestra aplicación por lo que podremos sobreescribirla con nuestra base de datos.
            this.getReadableDatabase();

            try {


                copyDataBase();
                Log.d("DB", "COPIA LA BASE DE DATOS");

            } catch (IOException e) {
                throw new Error("Error copiando Base de Datos");
            }
        }

    }

    /**
     * Comprueba si la base de datos existe para evitar copiar siempre el fichero cada vez que se abra la aplicación.
     * @return true si existe, false si no existe
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{

            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

//si llegamos aqui es porque la base de datos no existe todavía.

        }
        if(checkDB != null){

            checkDB.close();

        }
        return checkDB != null ? true : false;
    }

    /**
     * Copia nuestra base de datos desde la carpeta assets a la recién creada
     * base de datos en la carpeta de sistema, desde dónde podremos acceder a ella.
     * Esto se hace con bytestream.
     * */
    private void copyDataBase() throws IOException {

        Log.d("DB", "empieza a copiar la base de datos");

//Abrimos el fichero de base de datos como entrada
        InputStream myInput = myContext.getAssets().open(DB_NAME);

//Ruta a la base de datos vacía recién creada
        String outFileName = DB_PATH + DB_NAME;

//Abrimos la base de datos vacía como salida
        OutputStream myOutput = new FileOutputStream(outFileName);

//Transferimos los bytes desde el fichero de entrada al de salida
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

//Liberamos los streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
        Log.d("DB", "TERMINA DE COPIAR LA BASE DE DATOS");

    }

    public void open() throws SQLException {

//Abre la base de datos
        try {
            Log.d("DB", "trata de crear la db");
            createDataBase();
        } catch (IOException e) {
            throw new Error("Ha sido imposible crear la Base de Datos");
        }

        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(Constants.CREATE_STRUCTURE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS wlan");
        //onCreate(db);
    }

    public Cursor readVendor(){

        Log.d("DB", " READWLAN...");
        myDataBase= this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Constants._ID,
                Constants._ID_VENDOR,
                Constants._VENDOR_NAME,
        };

        String[] valuesWhere = {"7"};
        //OBTENIENDO LA CONSULTA

        cursor= myDataBase.query(
                Constants.TABLE_NAME,             // The table to query
                projection,                               // The columns to return
                "_id < ?",                                     // The columns for the WHERE clause
                valuesWhere,                                   // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                     // The sort order
        );

        //db.close();

        return cursor;
    }

/**
 * A continuación se crearán los métodos de lectura, inserción, actualización
 * y borrado de la base de datos.
 * */
}
