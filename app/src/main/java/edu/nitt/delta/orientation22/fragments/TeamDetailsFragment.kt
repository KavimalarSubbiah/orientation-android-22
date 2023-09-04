package edu.nitt.delta.orientation22.fragments

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import edu.nitt.delta.orientation22.LiveActivity
import edu.nitt.delta.orientation22.compose.screens.TeamDetailsScreen
import edu.nitt.delta.orientation22.compose.toast
import edu.nitt.delta.orientation22.di.viewModel.actions.LoginAction
import edu.nitt.delta.orientation22.di.viewModel.actions.TeamAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.LoginStateViewModel
import edu.nitt.delta.orientation22.di.viewModel.uiState.RegistrationState
import edu.nitt.delta.orientation22.di.viewModel.uiState.TeamStateViewModel

@Composable
fun TeamDetailsFragment(
    teamStateViewModel: TeamStateViewModel,
    loginStateViewModel: LoginStateViewModel
){

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        teamStateViewModel.doAction(TeamAction.GetLeader)
        var teamData=teamStateViewModel.teamData.value
        var state = teamStateViewModel.uiState.value
        loginStateViewModel.doAction(LoginAction.IsLive)
        if (state==RegistrationState.SUCCESS){
            state = RegistrationState.IDLE
                val intent = Intent(LocalContext.current, LiveActivity::class.java)
                LocalContext.current.startActivity(intent)
        } else if(state==RegistrationState.ERROR){
            state = RegistrationState.IDLE
            LocalContext.current.toast(teamStateViewModel.error.toString())
        }
        TeamDetailsScreen(teamDetails = teamData, state = state, registerTeam =  {
            teamStateViewModel.doAction(
                TeamAction.RegisterTeam(it)
            )
        }
        )
    }
}