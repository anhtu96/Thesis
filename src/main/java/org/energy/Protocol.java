package org.energy;

import org.energy.database.ActiveDevice;
import org.energy.model.Command;

import java.util.Collection;
import java.util.List;

public interface Protocol {

    String getName();

    Collection<String> getSupportedCommands();

    void sendCommand(ActiveDevice activeDevice, Command command);

//    void initTrackerServers(List<TrackerServer> serverList);

}
