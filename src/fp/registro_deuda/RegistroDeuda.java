package fp.registro_deuda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fp.utiles.Checkers;

public class RegistroDeuda {
	private String nombrePais;
	private LocalDate fecha;
	private Integer deudaTotal;
	private Double deudaPorcentajePIB;
	private Integer deudaPerCapita;
	private List<RegistroDeuda> registros;
	
	
	// constructor c1
	public RegistroDeuda(String nombrePais,LocalDate fecha,Integer deudaTotal,Double deudaPorcentajePIB,Integer deudaPerCapita) {
		
		Checkers.check(nombrePais, null);
		
		this.nombrePais=nombrePais;
		this.fecha=fecha;
		this.deudaTotal=deudaTotal;
		this.deudaPorcentajePIB=deudaPorcentajePIB;
		this.deudaPerCapita=deudaPerCapita;
		
	}
	
	//contructor c2
	public RegistroDeuda(String linea) {
		Checkers.checkNoNull(linea);
		String[] trozos=linea.split(";");
		Checkers.check("Formato no valido", trozos.length==5);
		String nombrePais=trozos[0].trim();
		Integer anyo=Integer.parseInt(trozos[1].trim());
		LocalDate fecha=LocalDate.of(anyo, 1, 1);
		Integer deudaTotal=Integer.parseInt(trozos[2].trim());
		Double deudaPorcentajePIB=Double.parseDouble(trozos[3].trim());
		Integer deudaPerCapita=Integer.parseInt(trozos[4].trim());
		
		Checkers.check("Ningúun atributo puede contener un valor nulo", nombrePais!=null || anyo!=null || fecha!=null||
				deudaTotal!=null|| deudaPorcentajePIB!=null||deudaPerCapita!=null);
		Checkers.check("Las fechas no pueden ser anteriores al 1/1/1945", fecha.compareTo(LocalDate.of(1945, 1, 1))>=0);
		Checkers.check("Los valores numéricos debe ser mayores o iguales a 0", deudaTotal>=0  );
		Checkers.check("Los valores numéricos debe ser mayores o iguales a 0",deudaPorcentajePIB>=0  );
		Checkers.check("Los valores numéricos debe ser mayores o iguales a 0",deudaPerCapita>=0  );
		
		this.nombrePais=nombrePais;
		this.fecha=fecha;
		this.deudaTotal=deudaTotal;
		this.deudaPorcentajePIB=deudaPorcentajePIB;
		this.deudaPerCapita=deudaPerCapita;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(fecha, nombrePais);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistroDeuda other = (RegistroDeuda) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(nombrePais, other.nombrePais);
	}

	public int compareTo(RegistroDeuda o) {
		int res=this.getNombrePais().compareTo(o.getNombrePais());
		if(res==0) {
			res=this.getFecha().compareTo(o.getFecha());
		}return res;
	}
	
	public RegistroDeuda() {
		registros=new ArrayList<>();
	}
	
	public RegistroDeuda(Stream<RegistroDeuda> registros) {
		this.registros=registros.collect(Collectors.toList());
	}
	
	public RegistroDeuda(List<RegistroDeuda> registros) {
		this.registros=new ArrayList<RegistroDeuda>(registros);
	}
		
	public RegistroDeuda(Collection<RegistroDeuda> registros) {
		this.registros=new ArrayList<RegistroDeuda>(registros);
	}
	
	public Integer poblacionAproximada(){
		return (int) Math.ceil(getDeudaTotal()/getDeudaPerCapita());
	}
	
	public Integer PIBAproximado() {
		return (int) Math.floor(getDeudaPorcentajePIB()/100);
	}


	public Integer getDeudaTotal() {
		return deudaTotal;
	}


	public void setDeudaTotal(Integer deudaTotal) {
		this.deudaTotal = deudaTotal;
	}


	public Double getDeudaPorcentajePIB() {
		return deudaPorcentajePIB;
	}


	public void setDeudaPorcentajePIB(Double deudaPorcentajePIB) {
		this.deudaPorcentajePIB = deudaPorcentajePIB;
	}


	public Integer getDeudaPerCapita() {
		return deudaPerCapita;
	}


	public void setDeudaPerCapita(Integer deudaPerCapita) {
		this.deudaPerCapita = deudaPerCapita;
	}


	public String getNombrePais() {
		return nombrePais;
	}


	public LocalDate getFecha() {
		return fecha;
	}
	
	//ejercicio 1
	
	public SortedSet<String> getNombrePaisesEmpiezaCon(String s) {
		return registros.stream()
				.filter(c->c.getNombrePais().toLowerCase().startsWith(s.toLowerCase()))
				.map(RegistroDeuda::getNombrePais)
				.sorted()
				.distinct()
				.collect(Collectors.toCollection(TreeSet::new));
		
	}
	
	//ejercicio 2
	
	public String getPaisMasDeudaPerCapita(List<String> nombrePaises,Integer anyo) {
		return registros.stream()
				.filter(c->c.getFecha().getYear()==anyo && nombrePaises.contains(c.getNombrePais()))
				.max(Comparator.comparing(RegistroDeuda::getDeudaPerCapita))
				.map(RegistroDeuda::getNombrePais)
				.orElse(null);
	
	}
	
	//ejercicio 3
	
	public SortedMap<String,List<RegistroDeuda>> getRegistrosPorPaisEnOrdenCronologico(){
		return registros.stream()
				.collect(Collectors.groupingBy(RegistroDeuda::getNombrePais,TreeMap::new,Collectors.collectingAndThen(Collectors.toList(), Lista->obteieneLista(Lista))));
	}

	private List<RegistroDeuda> obteieneLista(List<RegistroDeuda> lista) {
		return lista.stream()
				.sorted(Comparator.comparing(RegistroDeuda::getFecha).reversed())
				.collect(Collectors.toList());
	}
	
	//ejercicio 4
	
	public SortedMap<String,Double> getPorcentajeCambioDeudaTotalPorPaises() {
		SortedMap<String,List<RegistroDeuda>> m=getRegistrosPorPaisEnOrdenCronologico();
		return m.entrySet().stream()
				.filter(r->r.getValue().size()>1)
				.collect(Collectors.toMap(r->r.getKey(), r->calculaDif(r.getValue()),(x,y)->x,TreeMap::new));
		
	}
	
	private Double calculaDif(List<RegistroDeuda> datos) {
		RegistroDeuda fin=datos.get(0);
		RegistroDeuda inicio=datos.get(datos.size()-1);
		return 100.0*(fin.getDeudaTotal()-inicio.getDeudaTotal())/inicio.getDeudaTotal();
	}
	
	
	
	

}
