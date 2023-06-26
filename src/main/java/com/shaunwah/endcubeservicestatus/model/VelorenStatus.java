package com.shaunwah.endcubeservicestatus.model;

import lombok.Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

@Data
public class VelorenStatus {
    private final String METRICS_ENDPOINT = "http://veloren.endcube.net:14005/metrics";
    private String velorenBuildInfo;
    private Integer participantsConnected = 0;
    private Integer participantsDisconnected = 0;

    public VelorenStatus() throws Exception {
        URL url = URI.create(METRICS_ENDPOINT).toURL();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        url.openConnection().getInputStream()
                )
        );

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            final String PARTICIPANTS_CONNECTED = "participants_connected_total ";
            if (line.startsWith(PARTICIPANTS_CONNECTED)) {
                this.setParticipantsConnected(
                        Integer.parseInt(
                                line.replace(PARTICIPANTS_CONNECTED, "")
                        )
                );
            }

            final String PARTICIPANTS_DISCONNECTED = "participants_disconnected_total ";
            if (line.startsWith(PARTICIPANTS_DISCONNECTED)) {
                this.setParticipantsDisconnected(
                        Integer.parseInt(
                                line.replace(PARTICIPANTS_DISCONNECTED, "")
                        )
                );
            }

            final String VELOREN_BUILD_INFO = "veloren_build_info";
            if (line.startsWith(VELOREN_BUILD_INFO)) {
                this.setVelorenBuildInfo(
                        line.replaceAll(".+=\"(.{8}).+", "$1")
                );
            }
        }
    }
}
