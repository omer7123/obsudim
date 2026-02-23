package com.obsudim.mypsychologist.ui.profile.profileFragment

import android.Manifest
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.obsudim.mypsychologist.databinding.FragmentNotificationBinding
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.presentation.notification.NotificationScreenState
import com.obsudim.mypsychologist.presentation.notification.NotificationViewModel
import com.obsudim.mypsychologist.ui.core.composeComponents.PlaceholderError
import com.obsudim.mypsychologist.ui.theme.AppTheme
import java.util.Calendar
import javax.inject.Inject

class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var vmFactory: NotificationViewModel.Factory
    private val viewModel: NotificationViewModel by viewModels { vmFactory }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->

            if (isGranted) {
                viewModel.loadData(true)
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().profileComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationBinding.inflate(inflater, container, false)

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme {
                    NotificationContent()
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.includeToolbar.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
    
    override fun onResume() {
        super.onResume()

        val hasPermission = hasNotificationPermission()

        val currentState = viewModel.screenState.value
        if (currentState is NotificationScreenState.Content &&
            currentState.hasPermission != hasPermission
        ) {
            viewModel.loadData(hasPermission)
        }
    }

    @Composable
    private fun NotificationContent() {
        LaunchedEffect(Unit) {
            viewModel.loadData(hasNotificationPermission())
        }

        val state by viewModel.screenState.collectAsState()

        when (val result = state) {

            is NotificationScreenState.Content -> {

                NotificationRenderContent(
                    selectedTime = result.selectedTime,
                    hasPermission = result.hasPermission,
                    onTimeSelected = { h, m ->
                        viewModel.updateSelectedTime(h, m)
                    },
                    onRequestPermission = {
                        handlePermissionRequest()
                    }
                )
            }

            is NotificationScreenState.Loading -> {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            is NotificationScreenState.Error -> {
                PlaceholderError()
            }

            NotificationScreenState.Initial -> Unit
        }
    }

    private fun hasNotificationPermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true

        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun handlePermissionRequest() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        when {
            hasNotificationPermission() -> {
                viewModel.loadData(true)
            }

            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }

            else -> {
                openNotificationSettings()
            }
        }
    }

    private fun openNotificationSettings() {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
        }
        startActivity(intent)
    }

    @Composable
    fun NotificationRenderContent(
        selectedTime: String,
        hasPermission: Boolean,
        onTimeSelected: (Int, Int) -> Unit,
        onRequestPermission: () -> Unit
    ) {
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                style = AppTheme.typography.titleCygreFont,
                text = "Напоминания о заботе",
                color = AppTheme.colors.primaryText,
                modifier = Modifier
                    .padding(bottom = 30.dp)
            )

            if (!hasPermission) {
                Text(
                    style = AppTheme.typography.bodyL,
                    text = "Разрешите уведомления, и мы будем \n" +
                            "мягко напоминать о практиках, когда вам \n" +
                            "это удобно. Без спама, только \n" +
                            "поддержка \uD83D\uDC99.",
                    color = AppTheme.colors.primaryText,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                )

                Button(
                    onClick = onRequestPermission,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors( containerColor = AppTheme.colors.primaryText )
                ) {
                    Text(
                        text = "Настроить",
                        style = AppTheme.typography.bodyLBold,
                        color = AppTheme.colors.primaryTextInvert
                    )
                }
            } else {
                Text(
                    style = AppTheme.typography.bodyXL,
                    text = "Буду работать с приложением в $selectedTime",
                    color = AppTheme.colors.primaryText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp)
                )

                Button(
                    onClick = { showTimePickerDialog( context = context, currentTime = selectedTime, onTimeSelected = onTimeSelected ) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors( containerColor = AppTheme.colors.primaryText )
                ) {
                    Text(
                        text = "Настроить",
                        style = AppTheme.typography.bodyLBold,
                        color = AppTheme.colors.primaryTextInvert
                    )
                }
            }
        }
    }

    private fun showTimePickerDialog(
        context: Context,
        currentTime: String,
        onTimeSelected: (Int, Int) -> Unit
    ) {
        val calendar = Calendar.getInstance()
        val parts = currentTime.split(":")
        calendar.set(Calendar.HOUR_OF_DAY, parts[0].toInt())
        calendar.set(Calendar.MINUTE, parts[1].toInt())

        TimePickerDialog(
            context,
            { _: TimePicker, hour: Int, minute: Int ->
                onTimeSelected(hour, minute)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    AppTheme {
        Column {
            NotificationFragment().NotificationRenderContent(
                selectedTime = "19:00",
                hasPermission = true,
                onTimeSelected = { hour, minute ->
                },
                onRequestPermission = {
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationScreenNoPermissionPreview() {
    AppTheme {
        Column {
            NotificationFragment().NotificationRenderContent(
                selectedTime = "19:00",
                hasPermission = false,
                onTimeSelected = { hour, minute ->
                },
                onRequestPermission = {
                }
            )
        }
    }
}