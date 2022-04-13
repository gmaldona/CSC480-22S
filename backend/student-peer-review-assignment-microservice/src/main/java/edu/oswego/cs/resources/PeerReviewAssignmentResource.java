package edu.oswego.cs.resources;

import com.ibm.websphere.jaxrs20.multipart.IAttachment;
import edu.oswego.cs.daos.FileDAO;
import edu.oswego.cs.database.PeerReviewAssignmentInterface;
import edu.oswego.cs.distribution.AssignmentDistribution;
import org.bson.Document;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Path("assignments")
public class PeerReviewAssignmentResource {

    @GET
    @Path("{courseID}/{assignmentID}/assign/{count_to_review}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response assignTeams(
            @PathParam("courseID") String courseID,
            @PathParam("assignmentID") int assignmentID,
            @PathParam("count_to_review") int count) throws Exception {
        PeerReviewAssignmentInterface peerReviewAssignmentInterface = new PeerReviewAssignmentInterface();

        //List<String> teamNames = peerReviewAssignmentInterface.getCourseTeams(courseID);
        List<String> teamNames = peerReviewAssignmentInterface.getCourseStudentIDs(courseID);
        Map<String, List<String>> assignedTeams;
        try {
             assignedTeams = AssignmentDistribution.distribute(teamNames, count);
        } catch (IndexOutOfBoundsException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Number of reviews peer team is greater than the number of teams in the course.").build();
        }

        FileDAO.zipPeerReview(assignedTeams, courseID, assignmentID);

        return Response.status(Response.Status.OK).build();
        //Document assignedTeamDocument = peerReviewAssignmentInterface.addAssignedTeams(assignedTeams, courseID, assignmentID);
        //return Response.status(Response.Status.OK).entity(assignedTeamDocument).build();
    }

    @GET
    @Path("{courseID}/{assignmentID}/{teamName}/package/download")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public Response downloadOtherTeamsAssignments(
            @PathParam("courseID") String courseID,
            @PathParam("assignmentID") int assignmentID,
            @PathParam("teamName") String teamName
    ) {

        File file = new File(FileDAO.peer_review_path + courseID + "/"+assignmentID+"/for-"+teamName.concat(".zip"));

        Response.ResponseBuilder response = Response.ok(file);
        response.header("Content-Disposition", "attachment; filename=" + file.getName());
        return response.build();
    }

    @POST
    @Path("{courseID}/{assignmentID}/{srcTeamName}/{destTeamName}/upload")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPeerReview(
            List<IAttachment> attachments,
            @PathParam("courseID") String courseID,
            @PathParam("assignmentID") int assignmentID,
            @PathParam("srcTeamName") String srcTeamName,
            @PathParam("destTeamName") String destTeamName
    ) throws IOException {
        PeerReviewAssignmentInterface peerReviewAssignmentInterface = new PeerReviewAssignmentInterface();
        for (IAttachment attachment : attachments) {
            if (attachment == null) continue;
            String fileName = attachment.getDataHandler().getName();
            if (!fileName.endsWith("pdf")) return Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE).build();
            peerReviewAssignmentInterface.uploadPeerReview(courseID, assignmentID, srcTeamName, destTeamName, attachment);
        }
        return Response.status(Response.Status.OK).entity("Successfully uploaded peer review.").build();
    }

//    @GET
//    @Path("{courseID}/{assignmentID}/{teamName}/download")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response downloadPeerReview(
//        @PathParam("courseID") String courseID,
//        @PathParam("assignmentID") int assignmentID,
//        @PathParam("teamName") String teamName
//        ) {
//
//    }



}
