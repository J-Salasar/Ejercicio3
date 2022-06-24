package xyz.buscaminas.ejemplo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import xyz.buscaminas.ejemplo3.Procesos.SQLiteConexion;
import xyz.buscaminas.ejemplo3.Procesos.Transacciones;

public class ActivityConsulta extends AppCompatActivity {
    SQLiteConexion conexion;
    EditText id,nombre,apellido,edad,correo;
    Button consultar,eliminar,actializar,veras,inicio;
    Intent pagina;
    int turno=1;
    public boolean validar(String dato,int numero){
        String opcion1="[a-z,0-9]{3,50}[@][a-z,0-9]{3,50}[.][a-z]{2,10}";
        String opcion2="[a-z,0-9]{3,50}[.][a-z,0-9]{3,50}[@][a-z,0-9]{3,50}[.][a-z]{2,10}";
        String opcion3="[a-z,0-9]{3,50}[.][a-z,0-9]{3,50}[.][a-z,0-9]{3,50}[@][a-z,0-9]{3,50}[.][a-z]{2,10}";
        String opcion4="[A-Z,Á,É,Í,Ó,Ú,Ñ][a-z,á,é,í,ó,ú,ñ]{2,50}";
        String opcion5="[A-Z,Á,É,Í,Ó,Ú,Ñ][a-z,á,é,í,ó,ú,ñ]{2,50}[ ][A-Z,Á,É,Í,Ó,Ú,Ñ][a-z,á,é,í,ó,ú,ñ]{2,50}";
        switch(numero){
            case 1:{
                return dato.matches(opcion4+"|"+opcion5);
            }
            case 2:{
                return dato.matches(opcion4+"|"+opcion5);
            }
            case 3:{
                return dato.matches("[0-9]{1,2}");
            }
            case 4:{
                return dato.matches(opcion1+"|"+opcion2+"|"+opcion3);
            }
            default:{
                return false;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        init();
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar();
            }
        });
        actializar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar(nombre.getText().toString().trim(),turno)){
                    turno=2;
                    if(validar(apellido.getText().toString().trim(),turno)){
                        turno=3;
                        if(validar(edad.getText().toString().trim(),turno)){
                            turno=4;
                            if(validar(correo.getText().toString().trim(),turno)){
                                turno=1;
                                actializa();
                            }
                            else{
                                turno=1;
                            }
                        }
                        else{
                            turno=1;
                        }
                    }
                    else{
                        turno=1;
                    }
                }
                else{
                    turno=1;
                }
            }
        });
        veras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ver();
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elimina();
            }
        });
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inicial();
            }
        });
    }
    private void inicial(){
        pagina=new Intent(getApplicationContext(),ActivityCrear.class);
        startActivity(pagina);
    }
    private void elimina(){
        SQLiteDatabase db= conexion.getWritableDatabase();
        if(db!=null){
            db.execSQL("DELETE FROM "+Transacciones.empleados+
                        " WHERE "+Transacciones.id+"="+Integer.parseInt(id.getText().toString()));
            db.close();
            Toast.makeText(getApplicationContext(),"Se elimino el registro.",Toast.LENGTH_LONG).show();
            nombre.setEnabled(false);
            apellido.setEnabled(false);
            edad.setEnabled(false);
            correo.setEnabled(false);
            eliminar.setEnabled(false);
            actializar.setEnabled(false);
        }
        else{
            Toast.makeText(getApplicationContext(),"Error al eliminar",Toast.LENGTH_LONG).show();
        }
    }
    private void ver(){
        pagina=new Intent(getApplicationContext(),ActivityList.class);
        startActivity(pagina);
    }
    private void actializa(){
        SQLiteDatabase db= conexion.getWritableDatabase();
        if(db!=null){
            db.execSQL("UPDATE "+Transacciones.empleados+" SET "+
                        Transacciones.nombres+"='"+nombre.getText().toString()+"', "+
                        Transacciones.apellidos+"='"+apellido.getText().toString()+"', "+
                        Transacciones.edad+"='"+edad.getText().toString()+"', "+
                        Transacciones.correo+"='"+correo.getText().toString()+"' "+
                        "WHERE "+Transacciones.id+"="+Integer.parseInt(id.getText().toString()));
            db.close();
            Toast.makeText(getApplicationContext(),"Se actualizo los datos.",Toast.LENGTH_LONG).show();
            nombre.setEnabled(false);
            apellido.setEnabled(false);
            edad.setEnabled(false);
            correo.setEnabled(false);
            eliminar.setEnabled(false);
            actializar.setEnabled(false);
        }
        else{
            Toast.makeText(getApplicationContext(),"Error en actualizar",Toast.LENGTH_LONG).show();
        }
    }
    private void buscar(){
        try{
            SQLiteDatabase db= conexion.getWritableDatabase();
            String[] parametro={id.getText().toString()};
            String[] folders={  Transacciones.nombres,
                    Transacciones.apellidos,
                    Transacciones.edad,
                    Transacciones.correo
            };
            String condicion=Transacciones.id+"=?";
            Cursor data=db.query(Transacciones.empleados,folders,condicion,parametro,null,null,null);
            data.moveToFirst();
            if(data.getCount()>0){
                nombre.setText(data.getString(0));
                apellido.setText(data.getString(1));
                edad.setText(data.getString(2));
                correo.setText(data.getString(3));
                Toast.makeText(getApplicationContext(),"Se encontro este resultado.",Toast.LENGTH_LONG).show();
                nombre.setEnabled(true);
                apellido.setEnabled(true);
                edad.setEnabled(true);
                correo.setEnabled(true);
                eliminar.setEnabled(true);
                actializar.setEnabled(true);
            }
            else{
                Toast.makeText(getApplicationContext(),"No hay nada en ese 'ID'.",Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
    }

    private void init(){
        conexion=new SQLiteConexion(this, Transacciones.DataBase,null,1);
        consultar=(Button) findViewById(R.id.buscarB);
        eliminar=(Button) findViewById(R.id.elimina);
        actializar=(Button) findViewById(R.id.actualiza);
        veras=(Button) findViewById(R.id.ver);
        inicio=(Button) findViewById(R.id.inicio);
        id=(EditText) findViewById(R.id.buscarT);
        nombre=(EditText) findViewById(R.id.txtnombre21);
        apellido=(EditText) findViewById(R.id.txtapellido21);
        edad=(EditText) findViewById(R.id.txtedad21);
        correo=(EditText) findViewById(R.id.txtcorreo21);
    }
}