package pbil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import models.ConfigType;
public class NextLevelPBIL {
	static Random m = new Random();
	public static void main(String[] args) {
		//Comite {Classifiers{parametros}}
		HashMap<String ,HashMap<String, HashMap<String, String>>> comites = new HashMap<String ,HashMap<String, HashMap<String, String>>>();
		HashMap<String ,HashMap<String, HashMap<String, Integer>>> comitespv = new HashMap<String ,HashMap<String, HashMap<String, Integer>>>();
		HashMap<Double,HashMap<String,HashMap<String, HashMap<String, String>>>> population = new HashMap<Double,HashMap<String,HashMap<String, HashMap<String, String>>>>();
		for (int i =0; i<7; i++) {
			int numClassifiers = m.nextInt(9);
			HashMap<String ,HashMap<String, String>> classifiers = new HashMap<String ,HashMap<String, String>>();
			HashMap<String ,HashMap<String, Integer>> classifierspv = new HashMap<String ,HashMap<String, Integer>>();
			for (int j = 0; j<numClassifiers; j++) {
				int numParams = m.nextInt(10);
				HashMap<String ,String> params = new HashMap<String ,String>();
				HashMap<String ,Integer> paramspv = new HashMap<String ,Integer>();
				for (int k=1; k<=numParams; k++) {
					int paramType = m.nextInt(5);
					int paramValue = m.nextInt();
					if(ConfigType.values()[paramType].equals(ConfigType.BOLLEAN) && paramValue<0) {
						params.put(ConfigType.values()[paramType]+"", "True");
						paramspv.put("True", 1);
						
					}else if(ConfigType.values()[paramType].equals(ConfigType.BOLLEAN) && paramValue>=0) {
						params.put(ConfigType.values()[paramType]+"", "False");
						paramspv.put("False", 1);
					}
					else {
						params.put(ConfigType.values()[paramType]+"", paramValue+"");
						paramspv.put(paramValue+"", 1);
					}
				}
				classifiers.put("Classificador "+j, params);
				classifierspv.put("Classificador "+j, paramspv);
			}
			comites.put("Metodo "+i, classifiers);
			comitespv.put("Metodo "+i, classifierspv);
			
		}
		
		for (int i =0; i<20; i++) {
			for (int j =0; j<50;j++) {
				String comite = draw(comitespv);
				var metodo = comites.get(comite);
				for (int k=0; k< metodo.size(); k++) {
					System.out.println(k);
					for (String valor : metodo.get("Classificador "+k).keySet()){
						if(!(valor.equals(ConfigType.BOLLEAN+""))) {
							metodo.get("Classificador "+k).put(valor, ""+m.nextInt());
							comites.put(comite,metodo);
							Double acuracy = m.nextDouble();
							population.put(acuracy,comites);
						}else {
							metodo.get("Classificador "+k).put(valor, "False");
							comites.put(comite,metodo);
							Double acuracy = m.nextDouble();
							population.put(acuracy,comites);
						}
					}
				}
			}
			Map<Double,HashMap<String,HashMap<String, HashMap<String, String>>>> sortedSolutions = new TreeMap<Double,HashMap<String,HashMap<String, HashMap<String, String>>>>(population);
			population = (HashMap<Double, HashMap<String, HashMap<String, HashMap<String, String>>>>) sortedSolutions;
			comitespv = pointSolutions(comitespv, population);
		}
		
	}
	private static HashMap<String, HashMap<String, HashMap<String, Integer>>> pointSolutions(
			HashMap<String, HashMap<String, HashMap<String, Integer>>> comitespv,
			HashMap<Double,HashMap<String, HashMap<String, HashMap<String, String>>>> population) {
		int i=0;
		for (Double chave: population.keySet()) {
			if(i<25){
				HashMap<String, HashMap<String,HashMap<String, String>>> aux = population.get(chave); 
				for(String metodo :population.get(chave).keySet()) {
					HashMap<String, HashMap<String, Integer>> aux1 = comitespv.get(population.get(chave)); 
					for (String classificador: aux1.keySet() ) {
						HashMap<String, Integer> aux2 = aux1.get(classificador);
						for (String parametro :aux2.keySet()) {
							aux2.put(parametro, aux2.get(parametro)+1);
						}
						aux1.put(classificador, aux2);
					}
					comitespv.put(metodo, aux1);
				}
			}else {
				population.remove(chave);
			}
			i++;
		}
		
		return comitespv;
	}
	private static String draw(HashMap<String ,HashMap<String, HashMap<String, Integer>>> comitespv){
		Integer soma =probablilitySum(comitespv);
		int aux = m.nextInt(soma);
		int i =0;
		boolean parar = false;
		for (i =1; i<7; i++) {
			for (int j =0; j<comitespv.get("Metodo "+i).size(); j++) {
				for( int valor : comitespv.get("Metodo "+i).get("Classificador "+j).values() ) {
					aux-=valor;
					if(aux<=0) {
						parar = true;
						break;
					}
				}
				if(parar) {
					break;
				}
			}
			if(parar) {
				break;
			}
		}
		return "Metodo "+i;
	}
	private static int probablilitySum(HashMap<String ,HashMap<String, HashMap<String, Integer>>> comistespv) {
		int soma =0;
		for (int i = 0; i<7; i++) {
			for(int j =0; j<comistespv.get("Metodo "+i).size();j++) {
				for( int valor: comistespv.get("Metodo "+i).get("Classificador "+j).values()) {
					soma+= valor;
				}
			}
		}
		return soma;
	}
}
