package org.ks.trial.morp.amaterasu.dbo;

import com.vv.personal.prom.artifactory.proto.Problem;
import com.vv.personal.prom.artifactory.proto.Problems;
import org.ks.trial.morp.amaterasu.util.StringUtil;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
                .map(problem -> problem.getProblemName().trim().toLowerCase())
                .filter(ProblemDbo::verifyProblemDetails)
                .collect(Collectors.toList());
    }

    public static List<String> verifyProblemDetails(Collection<String> problems) { //maintaining convention
        return problems.stream()
                .map(problem -> problem.trim().toLowerCase())
                .filter(ProblemDbo::verifyProblemDetails)
                .collect(Collectors.toList());
    }

    public static boolean verifyProblemDetails(String problem) { //maintaining convention
        return isValidProblem(problem);
    }

    public static boolean isValidProblem(String problem) {
        return StringUtil.isValidInput(problem);
    }
}
