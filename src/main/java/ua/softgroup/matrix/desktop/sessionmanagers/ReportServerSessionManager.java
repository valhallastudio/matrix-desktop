package ua.softgroup.matrix.desktop.sessionmanagers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softgroup.matrix.api.ServerCommands;
import ua.softgroup.matrix.api.model.datamodels.ReportModel;
import ua.softgroup.matrix.api.model.datamodels.ReportsContainerDataModel;
import ua.softgroup.matrix.desktop.currentsessioninfo.CurrentSessionInfo;
import ua.softgroup.matrix.desktop.utils.CommandExecutioner;

import java.io.IOException;
import java.util.Set;


/**
 * @author Andrii Bei <sg.andriy2@gmail.com>
 */
public class ReportServerSessionManager {

    private static final Logger logger = LoggerFactory.getLogger(ReportServerSessionManager.class);
    private CommandExecutioner commandExecutioner;

    public ReportServerSessionManager() {
        commandExecutioner=new CommandExecutioner();
    }

    /**
     * Send to {@link CommandExecutioner} command for save or change report on Server
     * @param reportModel  reportModel current report get from user choice
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void saveOrChangeReportOnServer(ReportModel reportModel) {
        try {
            commandExecutioner.sendCommandWithNoResponse(ServerCommands.SAVE_REPORT, reportModel,
                    CurrentSessionInfo.getProjectId());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        logger.debug("Save or change report on server");
    }

    /**
     * Send to {@link CommandExecutioner} command for get all report for this project
     * @param id  id current project get from user choice
     * @return setReportModel
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Set<ReportModel> sendProjectDataAndGetReportById(long id)  {
        Set<ReportModel> setReportModel = null;
        try {
            setReportModel = ((ReportsContainerDataModel) commandExecutioner
                    .sendCommandWithResponse(ServerCommands.GET_REPORTS, id)).getReportModels();
        } catch (IOException | ClassNotFoundException e) {
            logger.debug("Reports was get unsuccessfully", e);
        }
        logger.debug("Get report by id from server");
        return setReportModel;
    }
}
