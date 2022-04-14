package edu.oswego.cs.resources;

import edu.oswego.cs.database.TeamInterface;
import edu.oswego.cs.requests.SwitchTeamParam;
import edu.oswego.cs.requests.TeamParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/teams")
public class PeerReviewTeamsResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("team/create")
    public Response createTeam(TeamParam request) {
        new TeamInterface().createTeam(request);
        return Response.status(Response.Status.CREATED).entity("Team successfully created.").build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("team/get/all")
    public Response getAllTeams(TeamParam request) {
        return Response.status(Response.Status.OK).entity(new TeamInterface().getAllTeams(request)).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("team/get/student_id")
    public Response getTeamByStudentID(TeamParam request) {
        return Response.status(Response.Status.OK).entity(new TeamInterface().getTeamByStudentID(request)).build();
    }

    @GET
    @Path("team/get/team_id")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeamByTeamID(TeamParam request) {
        return Response.status(Response.Status.OK).entity(new TeamInterface().getTeamByTeamID(request)).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("team/join")
    public Response joinTeam(TeamParam request) {
        new TeamInterface().joinTeam(request);
        return Response.status(Response.Status.OK).entity("Student successfully added to team.").build();
    }
    
    @PUT
    @Path("team/switch")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response switchTeam(SwitchTeamParam request) {
        new TeamInterface().switchTeam(request);
        return Response.status(Response.Status.OK).entity("Student successfully switched to a new team.").build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("team/generate-team-name")
    public Response generateTeamName(TeamParam request) {
        new TeamInterface().generateTeamName(request);
        return Response.status(Response.Status.OK).entity("Team name successfully generated").build(); 
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("team/professor/delete")
    public Response deleteTeam(TeamParam request) {
        new TeamInterface().deleteTeam(request);
        return Response.status(Response.Status.OK).entity("Team successfully deleted").build(); 
    }

    @PUT
    @Path("team/finalize-team")
    public Response finalizeTeam(String studenID, String targetedTeamID) {

        /* DESCRIPTION
            - requirements 4.2.3-11: Make sure the whole team click the "finalize button" to move on with the course (see other assignments)
            - Every one in the team can see the finalize button
            - If one clicks the finalize button, it triggers this API
            - For FE,
                + If this API returns 200 => the student successfully click the finalize button
                + If this API returns 400 => do nothing
        */

        /* TODO
            - Build TeamInterface::finalizeHandler() {
                + find the targetedTeam in DB using targetedTeamID
                + loop through the TargetdTeam.teamMembers.keySet()
                    * to get the element with the same studentID
                    * then update the value from false to true
                + then another loop through the TartedTeam.teamMembers hashmap to see if
                    * all the members' value is set to true => update targetedTeam.isFinalized to true
                    * at least one member's value is still false => do nothing (leave the .isFanalized as default i.e. false)
            }

            - Any exceptions here?
        */

        return Response.status(Response.Status.OK).entity("Finalize Successfully ").build(); // change the message
    }

    @GET
    @Path("team/is-team-finalized")
    public Boolean isTeamFinallized(String targetedTeamID) {
        /* DESCRIPTION
            - requirements 4.2.3-11: Make sure the whole team click the "finalize button" to move on with the course (see other assignments)
            - This API is particularly for FE
            - For FE,
                + If this API returns true  => the team is finalized
                + If this API returns false => do nothing
        */

        /* TODO
            - TeamInterface::isTeamFinalized() {
                + find the targetedTeam in DB using targetedTeamID
                + check targetedTeam.isFinalized
                    * if true   => the team is finalized
                    * if false  => do nothing
            }

        */

        return false; // change the message
    }

    /* TODO: 
        + Move/remove a user from a team (professor)
        + Delete Team (members.size() == 1) (TL + professor)
    */

}



