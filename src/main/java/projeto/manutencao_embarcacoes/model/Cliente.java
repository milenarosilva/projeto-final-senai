package projeto.manutencao_embarcacoes.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String razaoSocial;

	@Column(nullable = false, unique = true, length = 14)
	private String cnpj;

	@Column(nullable = false, unique = true)
	private String numeroContato;

	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
	private Set<Embarcacao> embarcacoes = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNumeroContato() {
		return numeroContato;
	}

	public void setNumeroContato(String numeroContato) {
		this.numeroContato = numeroContato;
	}

	public Set<Embarcacao> getEmbarcacoes() {
		return embarcacoes;
	}

	public void setEmbarcacoes(Set<Embarcacao> embarcacoes) {
		this.embarcacoes = embarcacoes;
	}

}
