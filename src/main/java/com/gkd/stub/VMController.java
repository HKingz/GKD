package com.gkd.stub;

public class VMController {
	public static VMType vmType;

	public static VMStub getVM() {
		if (vmType == VMType.Bochs) {
			return BochsStub.getInstance();
		} else if (vmType == VMType.Qemu) {
			return QemuStub.getInstance();
		} else {
			return null;
		}
	}

}
