package fp.testRegistroDeuda;

import java.util.List;


import fp.registro_deuda.FactoriaRegistros;
import fp.registro_deuda.RegistroDeuda;

public class TestRegistroDeuda {

	public static void main(String[] args) {
		List<RegistroDeuda> registros=FactoriaRegistros.leeDeudas("data/RegistroDeuda.csv");
		RegistroDeuda r=new RegistroDeuda(registros);
		System.out.println("\nTestGetNombrePaisesEmpiezaCon");
		System.out.println("===============================");
		testGetNombrePaisesEmpiezaCon(r,"España");
		System.out.println("\nTestGetPaisMasDeudaPerCapita");
		System.out.println("===============================");
		testGetPaisMasDeudaPerCapita(r, List.of("España","Portugal","Madrid"),2006);
		System.out.println("\nTestGetRegistrosPorPaisEnOrdenCronologico");
		System.out.println("===============================");
		testGetRegistrosPorPaisEnOrdenCronologico(r);
		System.out.println("\nTestGetPorcentajeCambioDeudaTotalPorPaises");
		System.out.println("===============================");
		testGetPorcentajeCambioDeudaTotalPorPaises(r);

	}

	private static void testGetNombrePaisesEmpiezaCon(RegistroDeuda r, String s) {
		try {
			String msg=String.format("Los nombres de los paises que empiezan por %d son: ",
					s,r.getNombrePaisesEmpiezaCon(s));
			System.out.println(msg);
		}catch(Exception e) {
			System.err.println("Excepcion inesesperada capturada"+ e.getMessage());
		}
		
	}
	
	private static void testGetPaisMasDeudaPerCapita(RegistroDeuda r, List<String> nombrePaises, Integer anyo) {
		try {
			String msg=String.format("El pais con mas deuda per Capital de la lista de paises %d y del año %s es: ",
					nombrePaises,anyo,r.getPaisMasDeudaPerCapita(nombrePaises, anyo));
			System.out.println(msg);
		}catch(Exception e) {
			System.err.println("Excepcion inesesperada capturada"+ e.getMessage());
		}
		
		
	}
	
	private static void testGetRegistrosPorPaisEnOrdenCronologico(RegistroDeuda r) {
		try {
			String msg=String.format("La lista de registros en orden cronológico por pais es el siguiente: "
					,r.getRegistrosPorPaisEnOrdenCronologico());
			System.out.println(msg);
		}catch(Exception e) {
			System.err.println("Excepcion inesesperada capturada"+ e.getMessage());
		}
	}
	
	private static void testGetPorcentajeCambioDeudaTotalPorPaises(RegistroDeuda r) {
		try {
			String msg=String.format("El porcentaje de cambio de deuda por pais es el siguiente:  "
					,r.getPorcentajeCambioDeudaTotalPorPaises());
			System.out.println(msg);
		}catch(Exception e) {
			System.err.println("Excepcion inesesperada capturada"+ e.getMessage());
		}
		
	}

}
