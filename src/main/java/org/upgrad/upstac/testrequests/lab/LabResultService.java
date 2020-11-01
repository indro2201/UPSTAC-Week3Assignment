package org.upgrad.upstac.testrequests.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.upgrad.upstac.testrequests.RequestStatus;
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.testrequests.TestRequestQueryService;
import org.upgrad.upstac.users.User;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Validated
public class LabResultService {


    @Autowired
    private LabResultRepository labResultRepository;

    @Autowired
    TestRequestQueryService testRequestQueryService;


    private static Logger logger = LoggerFactory.getLogger(LabResultService.class);



    private LabResult createLabResult(User tester, TestRequest testRequest) {
        //Implement this method to create the lab result module service
        // create object of LabResult class and use the setter methods to set tester and testRequest details
        // make use of saveLabResult() method to return the LabResult object

        // assigning test requests using ID
        LabResult labResult = new LabResult();
       labResult.setTester(tester);
       labResult.setRequest(testRequest);
       labResult.setResultId(tester.getId());

       return saveLabResult(labResult);
    }

    @Transactional
    LabResult saveLabResult(LabResult labResult) {
        return labResultRepository.save(labResult);
    }


    public LabResult assignForLabTest(TestRequest testRequest, User tester) {
        return createLabResult(tester, testRequest);
    }


    public LabResult updateLabTest(TestRequest testRequest, CreateLabResult createLabResult) {

        //Implement this method to update the lab test
        // create an object of LabResult and make use of setters to set Blood Pressure, Comments,
        // HeartBeat, OxygenLevel, Temperature, Result and UpdatedOn values
        // make use of the saveLabResult() method to return the object of LabResult

        //To update the test results

        LabResult labResult = labResultRepository.findByRequest(testRequest).get();

        labResult.setResult(createLabResult.getResult());
        labResult.setTemperature(createLabResult.getTemperature());
        labResult.setOxygenLevel(createLabResult.getOxygenLevel());
        labResult.setHeartBeat(createLabResult.getHeartBeat());
        labResult.setComments(createLabResult.getComments());
        labResult.setBloodPressure(createLabResult.getBloodPressure());
        labResult.setUpdatedOn(LocalDate.now());
        labResult.setTester(testRequest.getLabResult().getTester());
        labResult.setRequest(testRequest);

        return saveLabResult(labResult);
    }


    public List<TestRequest> getTestList() {
        //to get list of test requests
        return testRequestQueryService.findBy(RequestStatus.INITIATED);
    }

}
