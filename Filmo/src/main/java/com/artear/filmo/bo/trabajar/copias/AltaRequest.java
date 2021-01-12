package com.artear.filmo.bo.trabajar.copias;

public class AltaRequest {

	private MasterSC masterSC;
	private MasterCC masterCC;
	private RolloSC rolloSC;
	private RolloCC rolloCC;

	public MasterSC getMasterSC() {
		return masterSC;
	}

	public void setMasterSC(MasterSC masterSC) {
		this.masterSC = masterSC;
	}

	public MasterCC getMasterCC() {
		return masterCC;
	}

	public void setMasterCC(MasterCC masterCC) {
		this.masterCC = masterCC;
	}

	public RolloSC getRolloSC() {
		return rolloSC;
	}

	public void setRolloSC(RolloSC rolloSC) {
		this.rolloSC = rolloSC;
	}

	public RolloCC getRolloCC() {
		return rolloCC;
	}

	public void setRolloCC(RolloCC rolloCC) {
		this.rolloCC = rolloCC;
	}
	
	public String esCopiaOMaster(){
		if (masterSC != null || masterCC != null) {
			return "M";
		} else {
			return "C";
		}
	}

}
