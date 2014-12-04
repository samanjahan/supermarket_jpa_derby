/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superMarket;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * 
 * @author syst3m
 */

@NamedQueries({
	@NamedQuery(name = "findItemsByUser", query = "SELECT name FROM Item name WHERE name.owner = :owner"),
	@NamedQuery(name = "findAllItems", query = "SELECT Name FROM Item Name"),
	@NamedQuery(name = "deleteItem", query = "DELETE FROM Item item WHERE item = :item")})
@Entity(name = "Item")
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name="owner")
	private Person owner;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "price", nullable = false)
	private float price;

	public Item() {

	}

	public Item(String name, float price, Person owner) {
		this.name = name;
		this.price = price;
		this.owner = owner;
	}

	public float getPrice() {
		return price;
	}

	public Long getId() {
		return id;
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
		if (!(object instanceof Item)) {
			return false;
		}
		Item other = (Item) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "superMarket.Item[ id=" + id + " ]";
	}

	public String getName() {
		return name;
	}

	public Person getPerson() {
		return owner;
	}

	public String getPersonName() {
		return owner.getName() + "  ";
	}

}
