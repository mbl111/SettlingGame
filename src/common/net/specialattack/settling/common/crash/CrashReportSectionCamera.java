
package net.specialattack.settling.common.crash;

import net.specialattack.settling.client.util.camera.ICamera;
import net.specialattack.settling.common.util.Location;

public class CrashReportSectionCamera extends CrashReportSection {

    private ICamera camera;

    public CrashReportSectionCamera(ICamera camera, String cameraName) {
        super(cameraName);
        this.camera = camera;
    }

    @Override
    public String getData() {
        StringBuilder result = new StringBuilder();

        Location loc = this.camera.getLocation();

        result.append("Camera type: ").append(this.camera.getClass().getName());

        result.append("Current Location: ");
        result.append("(").append(String.format("%.3g", loc.x));
        result.append("; ").append(String.format("%.3g", loc.y));
        result.append("; ").append(String.format("%.3g", loc.z)).append(")").append("\r\n");
        result.append("Current Yaw: ").append(String.format("%.3g", loc.yaw)).append("\r\n");
        result.append("Current Pitch: ").append(String.format("%.3g", loc.pitch)).append("\r\n");

        Location prevLoc = this.camera.getPrevLocation();

        result.append("Previous Location: ");
        result.append("(").append(String.format("%.3g", prevLoc.x));
        result.append("; ").append(String.format("%.3g", prevLoc.y));
        result.append("; ").append(String.format("%.3g", prevLoc.z)).append(")").append("\r\n");
        result.append("Previous Yaw: ").append(String.format("%.3g", prevLoc.yaw)).append("\r\n");
        result.append("Previous Pitch: ").append(String.format("%.3g", prevLoc.pitch)).append("\r\n");

        return result.toString();
    }
}
