package com.kalimeradev.mipymee.client.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Digits;

import com.kalimeradev.mipymee.client.AppUtils;

@SuppressWarnings("serial")
public class Factura implements Serializable {
	private Long id;
	@NotNull
	@Size(min = 4)
	private String rfc;

	@NotNull
	@Digits(fraction = 2, integer = 3)
	private Double iva;

	@NotNull
	@Digits(fraction = 2, integer = 5)
	private Double total;

	private Date fechaFactura;
	private Date fechaNew;
	private Date fechaMod;
	private Long year;
	private Long month;

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}


	// ////////





	public Date getFechaNew() {
		return fechaNew;
	}

	public Date getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public void setFechaNew(Date fechaNew) {
		this.fechaNew = fechaNew;
	}

	public Date getFechaMod() {
		return fechaMod;
	}

	public void setFechaMod(Date fechaMod) {
		this.fechaMod = fechaMod;
	}

	public void setIva(String iva) {
		this.iva = AppUtils.createDouble(iva);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTotal(String total) {
		this.total = AppUtils.createDouble(total);
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public Long getMonth() {
		return month;
	}

	public void setMonth(Long month) {
		this.month = month;
	}

}
