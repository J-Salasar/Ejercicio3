package xyz.buscaminas.ejemplo3.Procesos;

public class Transacciones{
    public static final String empleados="empleados";
    public static final String id="id";
    public static final String nombres="nombres";
    public static final String apellidos="apellido";
    public static final String edad="edad";
    public static final String correo="correo";
    public static final String DataBase="Lista";
    public static final String CrearTablaEmpleado="CREATE TABLE "+empleados+" "+
            "("+
                id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                nombres+" TEXT,"+
                apellidos+" TEXT,"+
                edad+" TEXT,"+
                correo+" TEXT"+
            ")";
    public static final String DropTableEmpleado="DROP TABLE IF EXISTS "+empleados;
}
