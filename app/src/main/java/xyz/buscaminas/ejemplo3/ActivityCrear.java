package xyz.buscaminas.ejemplo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import xyz.buscaminas.ejemplo3.Procesos.SQLiteConexion;
import xyz.buscaminas.ejemplo3.Procesos.Transacciones;

public class ActivityCrear extends AppCompatActivity {
    Button enviar,atras;
    EditText txtnombre,txtapellido,txtedad,txtcorreo;
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
        setContentView(R.layout.activity_crear);
        txtnombre=(EditText) findViewById(R.id.txtnombre);
        txtapellido=(EditText) findViewById(R.id.txtapellido);
        txtedad=(EditText) findViewById(R.id.txtedad);
        txtcorreo=(EditText) findViewById(R.id.txtcorreo);
        enviar=(Button) findViewById(R.id.enviar);
        atras=(Button) findViewById(R.id.atras);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar(txtnombre.getText().toString().trim(),turno)){
                    turno=2;
                    if(validar(txtapellido.getText().toString().trim(),turno)){
                        turno=3;
                        if(validar(txtedad.getText().toString().trim(),turno)){
                            turno=4;
                            if(validar(txtcorreo.getText().toString().trim(),turno)){
                                turno=1;
                                AgregarEmpleado();
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
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volver();
            }
        });
    }
    private void volver(){
        pagina=new Intent(getApplicationContext(),ActivityConsulta.class);
        startActivity(pagina);
    }
    private void AgregarEmpleado(){
        SQLiteConexion conexion=new SQLiteConexion(this, Transacciones.DataBase,null,1);
        SQLiteDatabase db=conexion.getWritableDatabase();
        ContentValues valores=new ContentValues();
        valores.put(Transacciones.nombres,txtnombre.getText().toString());
        valores.put(Transacciones.apellidos,txtapellido.getText().toString());
        valores.put(Transacciones.edad,txtedad.getText().toString());
        valores.put(Transacciones.correo,txtcorreo.getText().toString());
        Long resultado=db.insert(Transacciones.empleados,Transacciones.id,valores);
        Toast.makeText(getApplicationContext(),"Registro guardado",Toast.LENGTH_LONG).show();
        db.close();
        ClearScreen();
    }

    private void ClearScreen(){
        txtnombre.setText("");
        txtapellido.setText("");
        txtedad.setText("");
        txtcorreo.setText("");
    }
}