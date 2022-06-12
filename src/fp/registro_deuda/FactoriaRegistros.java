package fp.registro_deuda;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import fp.utiles.Checkers;
import fp.utiles.Ficheros;

public class FactoriaRegistros {
	
	public static List<RegistroDeuda> leeDeudas(String fichero){
		Checkers.checkNoNull(fichero);
		List<String> lineas= Ficheros.leeFichero("Error al leer fichero", fichero);
		lineas.remove(0);
		
		List<RegistroDeuda> res=new ArrayList<RegistroDeuda>();
		for(String linea:lineas) {
			RegistroDeuda d=parseaDeuda(linea);
			res.add(d);
		}return res;
		
	}
	
	private static RegistroDeuda parseaDeuda(String lineas) {
		Checkers.checkNoNull(lineas);
		String[] trozos= lineas.split(";");
		String nombrePais=trozos[0].trim().toUpperCase();
		LocalDate fecha=LocalDate.parse(trozos[1].trim(),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Integer deudaTotal=Integer.parseInt(trozos[2].trim());
		Double deudaPorcentajePIB=Double.parseDouble(trozos[3].trim());
		Integer deudaPerCapita=Integer.parseInt(trozos[4].trim());
		return new RegistroDeuda(nombrePais,fecha,deudaTotal,deudaPorcentajePIB,deudaPerCapita);
		
		
		
	}

}
