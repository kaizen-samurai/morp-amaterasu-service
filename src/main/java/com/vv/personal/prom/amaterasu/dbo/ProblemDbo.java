package com.vv.personal.prom.amaterasu.dbo;

import com.vv.personal.prom.artifactory.proto.Problem;
import com.vv.personal.prom.artifactory.proto.Problems;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.vv.personal.prom.amaterasu.util.StringUtil.isValidInput;

/**
 * @author Vivek
 * @since 24/12/20
 */
public class ProblemDbo extends AbstractDbo {

    public static Integer generateProblemId(String problemName) {
        return generateId(problemName);
    }

    public static Problem generateProblemProto(Integer problemId, String problemName) {
        return Problem.newBuilder()
                .setProblemId(problemId)
                .setProblemName(problemName)
                .build();
    }

    public static Problems generateProblemsProto(List<Problem> problems) {
        Problems.Builder problemsBuilder = Problems.newBuilder();
        problems.forEach(problemsBuilder::addProblems);
        return problemsBuilder.build();
    }

    public static List<String> verifyProblemDetailsProto(Collection<Problem> problems) { //maintaining convention
        return problems.stream()
                .map(problem -> problem.getProblemName().strip().toLowerCase())
                .filter(ProblemDbo::verifyProblemDetails)
                .collect(Collectors.toList());
    }

    public static List<String> verifyProblemDetails(Collection<String> problems) { //maintaining convention
        return problems.stream()
                .map(problem -> problem.strip().toLowerCase())
                .filter(ProblemDbo::verifyProblemDetails)
                .collect(Collectors.toList());
    }

    public static boolean verifyProblemDetails(String problem) { //maintaining convention
        return isValidProblem(problem);
    }

    public static boolean isValidProblem(String problem) {
        return isValidInput(problem);
    }
}
