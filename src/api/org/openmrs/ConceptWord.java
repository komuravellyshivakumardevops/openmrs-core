package org.openmrs;

import java.util.HashSet;
import java.util.Set;

import org.openmrs.util.Helper;

/**
 * ConceptWord 
 */
public class ConceptWord implements java.io.Serializable, Comparable<ConceptWord> {

	public static final long serialVersionUID = 888677L;
	
	// Fields

	private Concept concept;
	private String word;
	private String synonym;
	private String locale;
	private Double weight=0.0;

	// Constructors

	/** default constructor */
	public ConceptWord() {
	}
	
	public ConceptWord(String word, Concept concept, String locale, String synonym) {
		this.concept = concept;
		this.word = word;
		this.locale = locale;
		this.synonym = synonym;
	}
	
	public ConceptWord(Concept c) {
		this.concept = c;
		this.word = null;
		this.locale = null;
		this.synonym = null;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof ConceptWord) {
			ConceptWord c = (ConceptWord)obj;
			boolean matches = true;
			if (concept != null && c.getConcept() != null)
				matches = matches && concept.equals(c.getConcept());
			if (word != null && c.getWord() != null)
				matches = matches && word.equals(c.getWord());
			if (locale != null && c.getLocale() != null)
				matches = matches && locale.equals(c.getLocale());
			if (synonym !=  null && c.getSynonym() != null)
				matches = matches && synonym.equals(c.getSynonym());
			return (matches);
		}
		return false;
	}
	
	public int hashCode() {
		int hash = 3;
		if (concept != null)
			hash = 37*hash + this.getConcept().hashCode();
		if (word != null)
			hash = 37*hash + this.getWord().hashCode();
		if (locale != null)
			hash = 37*hash + this.getLocale().hashCode();
		if (synonym !=  null)
			hash = 37*hash + this.getSynonym().hashCode();
		
		return hash;
	}
	
	/**
	 * @return Returns the locale.
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale The locale to set.
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @return Returns the synonym.
	 */
	public String getSynonym() {
		return synonym;
	}

	/**
	 * @param synonym The synonym to set.
	 */
	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}

	/**
	 * @return Returns the word.
	 */
	public String getWord() {
		return word;
	}

	/**
	 * @param word The word to set.
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * @return Returns the concept.
	 */
	public Concept getConcept() {
		return concept;
	}

	/**
	 * @param concept The concept to set.
	 */
	public void setConcept(Concept concept) {
		this.concept = concept;
	}	
	
	/**
	 * @return Returns the weight.
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * @param weight The weight to set.
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	/**
	 * Increment the weight by i 
	 * @param i
	 */
	public void increaseWeight(Double i) {
		this.weight += i;
	}
	
	/**
	 * 
	 * @param concept
	 * @return
	 */

	public static Set<ConceptWord> makeConceptWords(Concept concept) {
		Set<ConceptWord> words = new HashSet<ConceptWord>();
		
		for (ConceptName name : concept.getNames()) {
			Set<String> uniqueParts = getUniqueWords(name.getName());
			for (String part : uniqueParts) {
				words.add(new ConceptWord(part, concept, name.getLocale(), ""));
			}
		}
		
		for (ConceptSynonym synonym : concept.getSynonyms()) {
			String syn = synonym.getSynonym();
			Set<String> uniqueParts = getUniqueWords(syn);
			for (String part : uniqueParts) {
				words.add(new ConceptWord(part, concept, synonym.getLocale(), syn));
			}
		}
		
		return words;
	}
	
	public static Set<String> getUniqueWords(String phrase) {
		if (phrase.length() > 2) {
			phrase = phrase.replaceAll(Helper.OPENMRS_REGEX_LARGE, " ");
		}
		else {
			phrase = phrase.replaceAll(Helper.OPENMRS_REGEX_SMALL, " ");
		}
		String[] parts = phrase.trim().toUpperCase().replace('\n', ' ').split(" ");
		Set<String> uniqueParts = new HashSet<String>();
		
		for (String part : parts) {
			String p = part.trim();
			if (!p.equals("") &&
				!Helper.OPENMRS_STOP_WORDS.contains(p) && 
				!uniqueParts.contains(p))
					uniqueParts.add(p);
		}
		return uniqueParts;
	}
	
	public String toString() {
		String s = "";
		if (concept != null)
			s += concept.getConceptId() + "|";
		if (word != null)
			s += word + "|";
		if (synonym != null)
			s += synonym + "|";
		if (locale != null)
			s += locale;
		return s;
	}

	/**
	 * @see java.lang.Comparable#compareTo(T)
	 */
	public int compareTo(ConceptWord word) {
		
		return Double.compare(word.getWeight(), weight);
		
	}
		
}