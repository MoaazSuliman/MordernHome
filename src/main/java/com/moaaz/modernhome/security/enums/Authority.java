package com.moaaz.modernhome.security.enums;

public enum Authority   {
    view_course_access,// 1. /courses 2. /students/privateStudents 3. /organizations 4. /coursesSecurity/allowedFor/**
    update_course_access, // 1. /courseSecurity/allow
    view_student_submissions, // 1. /studentSubmissions
    update_student_submission, // 1. /studentSubmissions/putGrade/**/grade/**


    add_assignment,// 1. /lessons/getAll  2. /assignments POST 3. /assignments/uploadPdf
    view_assignments, // 1. /assignments GET  2. /lessons/getAll GET 3. /studentSubmissions/getAllForAssignments/**
    delete_assignment,// /assignments/** DELETE
    update_assignment, // 1./assignments/** PUT  2./lessons/getAll 3./assignments/uploadPdf



    add_worksheet,// 1. /lessons/getAll  2. /worksheets POST 3. /worksheets/uploadPdf
    view_worksheets, // 1. /worksheets/getAll GET  2. /lessons/getAll GET 3. /studentSubmissions/getAllForWorksheet/**
    delete_worksheet,// /worksheets/** DELETE
    update_worksheet, // 1./worksheets/** PUT  2./lessons/getAll 3./worksheets/uploadPdf


    add_video,// 1. /lessons/getAll  2. /videos POST 3. /videos/updateContent/**
    view_videos, // 1. /videos/getAll GET  2. /lessons/getAll GET
    delete_video,// /videos/** DELETE
    update_video, // 1./videos/** PUT  2./lessons/getAll 3. /videos/updateContent/**


    add_course, // 1. /courses/add-new-course POST
    view_courses,// 1. /courses GET  2. /course/getChapters/**
    update_course, // 1./course/** PUT
    delete_course,

    add_chapter, // 1. /chapters POST  2. /courses GET
    view_chapters,// 1. /chapters GET  2. /courses GET  3. /chapters/getLessons/** GET
    update_chapter, // 1./chapters/** PUT
    delete_chapter, // /chapters/** DELETE


    add_lesson, // 1. /lessons POST  2. /chapters GET
    view_lessons,// 1. /lessons/getAll GET  2. /chapters GET  3. /lessons/getWorksheets/** GET  3. /lessons/getAssignments/** GET 3. /lessons/getVideos/** GET
    update_lesson, // 1./lessons/** PUT
    delete_lesson, // /chapters/** DELETE

    // assistant , organizations , quiz

    add_student, // 1. /students POST  2. /organizations GET
    view_students,// 1. /students GET  2. /organizations GET 3. /students/getAllowedCourses/** GET
    update_student, // 1./students/** PUT
    delete_student, // /chapters/** DELETE


    add_organization, // 1. /organizations POST
    view_organization,// 1. /students GET  2. /organizations GET 3. /organizations/getStudents/** GET
    update_organization, // 1./organizations/** PUT
    delete_organization, // /organizations/** DELETE

    add_assistant, // 1. /assistant POST  2. /authorities GET
    view_assistants,// 1. /assistants GET  2. /authorities GET 3. /organizations/getStudents/** GET
    update_assistant, // 1./assistants/** PUT
    delete_assistant, // /assistants/** DELETE


    add_quiz, //http://localhost:9090/guc/courses GET, http://localhost:9090/guc/quizzes (POST)
    view_quizzes, //"http://localhost:9090/guc/quizzes ,http://localhost:9090/guc/courses , "http://localhost:9090/guc/quizzes/validQuizzes
    update_quiz, // 1./quizzes/** PUT
    delete_quiz, // /quizzes/** DELETE


}
