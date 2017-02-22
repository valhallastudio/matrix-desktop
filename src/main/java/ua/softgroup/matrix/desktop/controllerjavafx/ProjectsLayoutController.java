package ua.softgroup.matrix.desktop.controllerjavafx;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ua.softgroup.matrix.desktop.currentsessioninfo.CurrentSessionInfo;
import ua.softgroup.matrix.desktop.sessionmanagers.ReportServerSessionManager;
import ua.softgroup.matrix.server.desktop.model.ProjectModel;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

/**
 * @author Andrii Bei <sg.andriy2@gmail.com>
 */
public class ProjectsLayoutController {

    @FXML
    public TableView<ProjectModel> tvProjectsTable;
    @FXML
    public TableColumn<ProjectModel, Long> tcIdProject;
    @FXML
    public TableColumn<ProjectModel, String> tcAuthorName;
    @FXML
    public TableColumn<ProjectModel, String> tcTitle;
    @FXML
    public TableColumn<ProjectModel, String> tcDescription;
    @FXML
    public TableColumn tcStatus;
    @FXML
    public PieChart missPieCharts;
    @FXML
    public Label labelDayInWord;
    @FXML
    public Label labelDayInNumber;
    @FXML
    public Label labelNameSales;
    @FXML
    public Label labelNameProject;
    @FXML
    public Label labelDiscribeProject;
    @FXML
    public Label labelStartWorkToday;
    @FXML
    public Label labelTodayTotalTime;
    @FXML
    public Label labelTotalTime;
    @FXML
    public Label labelDateStartProject;
    @FXML
    public Label labelDeadLineProject;
    @FXML
    public Button btnStart;
    @FXML
    public Button btnStop;
    @FXML
    public TextArea taWriteReport;
    @FXML
    public Button btnAttachFile;
    @FXML
    public Button btnSendReport;
    @FXML
    public Label labelSymbolsNeedReport;
    static ObservableList<ProjectModel> projectsData = FXCollections.observableArrayList();
    // TODO format code
    private static DateTimeFormatter dateFormatNumber=DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static DateTimeFormatter dateFormatText=DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH);
    private ReportServerSessionManager reportServerSessionManager;


    @FXML
    private void initialize() throws IOException {
        initPieChart();
        initTable();
        getTodayDayAndSetInView();
    }

    private void getTodayDayAndSetInView() {
        LocalDate date =LocalDate.now();
        String dayOfWeekText =date.format(dateFormatText);
        String dayOfWeekNumber = date.format(dateFormatNumber);
        labelDayInWord.setText(dayOfWeekText);
        labelDayInNumber.setText(dayOfWeekNumber);
    }

    private void initTable() {
        //TODO use constants
        tcIdProject.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcAuthorName.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        setProjectInTable();
    }

    private void setProjectInTable() {
        Set<ProjectModel> projectModelSet = CurrentSessionInfo.getUserActiveProjects();
        if(projectModelSet!=null&&!projectModelSet.isEmpty()){
            projectModelSet.forEach(projectsData::add);
//        for (ProjectModel projectModel : projectModelSet) {
//            projectsData.add(projectModel);
//        }
            tvProjectsTable.setItems(projectsData);
            setOtherProjectInfoInView(projectsData.get(0));
        }
    }
    private void setOtherProjectInfoInView(ProjectModel projectModel) {
        CurrentSessionInfo.setProjectId(projectModel.getId());
            labelNameSales.setText(projectModel.getAuthorName());
            labelNameProject.setText(":" + projectModel.getTitle());
            labelDiscribeProject.setText(projectModel.getDescription());
           if ((projectModel.getStartDate()!=null && projectModel.getEndDate()!=null)){
            labelDateStartProject.setText(projectModel.getStartDate().format(dateFormatNumber));
            labelDeadLineProject.setText(projectModel.getEndDate().format(dateFormatNumber));
        }

    }

    private void initPieChart() {
        ObservableList<PieChart.Data>  pieChartList = FXCollections.observableArrayList(new PieChart.Data("Простой", 7),
                new PieChart.Data("Чистое", 93));
        missPieCharts.setData(pieChartList);
    }

    public void chosenProject(Event event) throws IOException {
        ProjectModel selectProject=tvProjectsTable.getSelectionModel().getSelectedItem();
        setOtherProjectInfoInView(selectProject);
    }
}
