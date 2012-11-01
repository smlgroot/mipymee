package com.kalimeradev.mipymee.client.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Digits;

import com.kalimeradev.mipymee.client.AppUtils;

@SuppressWarnings("serial")
public class Factura implements Serializable {
	@NotNull
	@Size(min = 4)
	private String rfc;

	@NotNull
	@Digits(fraction = 2, integer = 3)
	private Double iva;

	@NotNull
	@Digits(fraction = 2, integer = 5)
	private Double total;

	private Date fecha;

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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	// ////////

	public void setIva(String iva) {
		this.iva = AppUtils.createDouble(iva);
	}

	public void setTotal(String total) {
		this.total = AppUtils.createDouble(total);
	}

}
