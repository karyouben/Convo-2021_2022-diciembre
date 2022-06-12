package fp.testRegistroDeuda;

import java.util.List;

import fp.registro_deuda.FactoriaRegistros;
import fp.registro_deuda.RegistroDeuda;

public class TestFactoriaRegistros {

	public static void main(String[] args) {
		testLeeFichero("data/registroDeuda.csv");

	}

	private static void testLeeFichero(String fichero) {
		System.out.println("\nTestLeeFichero");
		List<RegistroDeuda> registros=FactoriaRegistros.leeDeudas(fichero);
		System.out.println(" RegistroDeuda: ");
		for(RegistroDeuda r:registros) {
			System.out.println(r);
		}
		
		
	}

}
