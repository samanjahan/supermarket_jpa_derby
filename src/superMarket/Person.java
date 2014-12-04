/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superMarket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@NamedQueries({
		@NamedQuery(name = "findAllUser", query = "SELECT name FROM Person Name "),
		@NamedQuery(name = "updateSold", query = "UPDATE Person p SET p.sold = :number WHERE p = :person"),
		@NamedQuery(name = "updateBought", query = "UPDATE Person p SET p.bought = :number WHERE p = :person"),
		@NamedQuery(name = "findUser", query = "SELECT Name FROM Person Name WHERE Name.name LIKE :userName") })
@Entity(name = "Person")
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<Item> items;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "pass", nullable = false)
	private String password;

	@Column(name = "bought")
	private int bought;

	@Column(name = "sold")
	private int sold;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public int getBought() {
		return bought;
	}

	public int getSold() {
		return sold;
	}

	public Person(String name, String password) {
		this.name = name;
		this.password = password;
		this.bought = 0;
		this.sold = 0;
	}

	public Person() {
		items = new ArrayList<Item>();
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Person)) {
			return false;
		}
		Person other = (Person) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	public void increaseBuy() {
		bought++;
	}

	public void increaseSold() {
		sold++;
	}

	@Override
	public String toString() {
		return "superMarket.Person[ id=" + id + " ]";
	}

}
