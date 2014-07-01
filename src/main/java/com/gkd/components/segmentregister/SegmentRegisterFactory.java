package com.gkd.components.segmentregister;

import com.gkd.stub.VMController;
import com.gkd.stub.VMType;

public final class SegmentRegisterFactory {
	public static SegmentRegister createSegmentRegister() {
		if (VMController.vmType == VMType.Bochs) {
			BochsSegmentRegister bochsSegmentRegister = new BochsSegmentRegister();
			return bochsSegmentRegister;
		} else if (VMController.vmType == VMType.Qemu) {
			QemuSegmentRegister qemuSegmentRegister = new QemuSegmentRegister();
			return qemuSegmentRegister;
		} else {
			return new SegmentRegister();
		}
	}
}