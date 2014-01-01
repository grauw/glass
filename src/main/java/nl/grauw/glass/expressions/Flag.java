/*
 * Copyright 2014 Laurens Holst
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.grauw.glass.expressions;

public class Flag extends Literal {
	
	public static Flag NZ = new Flag("nz", 0);
	public static Flag Z = new Flag("z", 1);
	public static Flag NC = new Flag("nc", 2);
	public static Flag C = new Flag("c", 3);
	public static Flag PO = new Flag("po", 4);
	public static Flag PE = new Flag("pe", 5);
	public static Flag P = new Flag("p", 6);
	public static Flag M = new Flag("m", 7);
	
	private final String name;
	private final int code;
	
	public Flag(String name, int code) {
		this.name = name;
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	@Override
	public Expression copy(Context context) {
		return this;
	}
	
	@Override
	public boolean isInteger() {
		return false;
	}
	
	@Override
	public int getInteger() {
		throw new EvaluationException("Can not evaluate flag to integer.");
	}
	
	@Override
	public boolean isFlag() {
		return true;
	}
	
	@Override
	public Flag getFlag() {
		return this;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public String toDebugString() {
		return toString();
	}
	
	public static Flag getByName(String name) {
		switch (name) {
		case "nz":
		case "NZ":
			return Flag.NZ;
		case "z":
		case "Z":
			return Flag.Z;
		case "nc":
		case "NC":
			return Flag.NC;
		case "c":
		case "C":
			return Flag.C;
		case "po":
		case "PO":
			return Flag.PO;
		case "pe":
		case "PE":
			return Flag.PE;
		case "p":
		case "P":
			return Flag.P;
		case "m":
		case "M":
			return Flag.M;
		}
		return null;
	}
	
}
