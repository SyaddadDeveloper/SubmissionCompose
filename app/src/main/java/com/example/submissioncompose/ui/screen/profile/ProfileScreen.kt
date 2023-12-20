package com.example.submissioncompose.ui.screen.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.submissioncompose.R
import com.example.submissioncompose.ui.theme.SubmissionComposeTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
) {
    ProfileContent(
        modifier = modifier,
        onBackClick = navigateBack
    )
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                tint = Color.Black,
                contentDescription = stringResource(R.string.back),
                modifier = Modifier
                    .padding(5.dp)
                    .clickable { onBackClick() }
            )
            Text(
                text = "About Me",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                modifier = modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = stringResource(R.string.profile_picture),
                    contentDescription = "Profile photo",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 8.dp)
                        .size(200.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Black, CircleShape)
                )
                Text(
                    text = stringResource(R.string.profile_name),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = stringResource(R.string.profile_email),
                    modifier = Modifier.padding(10.dp),
                    style =
                    TextStyle(
                        fontSize = 12.sp
                    )
                )
                Text(
                    text = stringResource(R.string.profile_email2),
                    modifier = Modifier.padding(10.dp),
                    style =
                    TextStyle(
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAboutContent() {
    SubmissionComposeTheme {
        ProfileContent {}
    }
}