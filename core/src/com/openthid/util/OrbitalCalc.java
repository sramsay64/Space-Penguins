package com.openthid.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

public class OrbitalCalc {
	private float a;
	private float p;
	private float e;
	private double augment;
	private double nu;

	public OrbitalCalc(Vector3 r, Vector3 v, float mu) {
		Vector3 h = new Vector3(r).crs(v);
		Vector3 nhat= new Vector3(0, 0, 1).crs(h);
		Vector3 ecc = new Vector3(r).scl(v.len2()-mu/r.len()).sub(new Vector3(v).scl(new Vector3(r).dot(v))).scl(1/mu) ;
		e = ecc.len();
		
		float energy = v.len2()/2 - mu/r.len();
		
		a = -mu/(2*energy);
		p = a*(1-ecc.len2());
		float xxa = new Vector3(nhat).dot(ecc);
		float xxb = (nhat.len()*e);
		float xxc = xxa/xxb;
//		float xxc = (new Vector3(nhat).dot(ecc))/(nhat.len()*e);
		augment = Math.acos(xxc);
		nu = Math.acos((new Vector3(r).dot(ecc))/(r.len()*e));
		
		Gdx.app.log("TEMP", "" + nu);
	}

	public float getA() {
		return a + p;
	}

	public double getB() {
		return getA()*Math.sqrt(1-Math.pow(e, 2));
	}

	public double getAugment() {
		return augment;
	}
}