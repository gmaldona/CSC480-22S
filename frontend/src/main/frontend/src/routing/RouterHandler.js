import {BrowserRouter, Route, Routes} from "react-router-dom";
import App from "../App";
import CreateCoursePage from "../pages/CreateCoursePage";
import TeacherDashboardPage from "../pages/TeacherDashboardPage";
import EditCoursePage from "../pages/EditCoursePage";
import React from "react";

const RouterHandler = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<App />} />
                <Route path="createCourse" element={<CreateCoursePage />} />
                <Route path="teacherDashboard" element={<TeacherDashboardPage />} />
                <Route path="editCourse" element={<EditCoursePage />} />
            </Routes>
        </BrowserRouter>
    )
}

export default RouterHandler;