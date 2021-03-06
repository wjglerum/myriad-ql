﻿using Questionnaires.ErrorHandling;
using System.Collections.Generic;

namespace Questionnaires.QLS.SemanticAnalysis
{
    class PlacementChecker
    {
        private List<RunTime.Question> PlacedQuestions = new List<RunTime.Question>();
        private Dictionary<string, RunTime.Question> QLQuestions = new Dictionary<string, RunTime.Question>();
        private Result Result;

        public PlacementChecker(Result result, IEnumerable<RunTime.Question> questionsInQL)
        {
            Result = result;

            foreach (var question in questionsInQL)
            {
                QLQuestions[question.Identifier] = question;
            }
        }

        public bool CheckQuestion(string name)
        {
            if (!QLQuestions.ContainsKey(name))
            {
                Result.AddEvent(new Error(string.Format("Question {0} defined in QLS is not defined in the QL file", name)));
                return false;
            }

            var question = QLQuestions[name];

            // Check the question has not been placed before
            if (PlacedQuestions.Contains(question))
            {
                Result.AddEvent(new Error(string.Format("Question {0} was already placed", name)));
                return false;
            }

            PlacedQuestions.Add(question);

            return true;
        }

        public void CheckIfAllQuestionsArePlaced()
        {
            foreach (var qlQuestion in QLQuestions.Values)
            {
                if (!PlacedQuestions.Contains(qlQuestion))
                {
                    Result.AddEvent(new Error(string.Format("Question {0} in the QL file is notplaced by the QLS file", qlQuestion.Identifier)));
                }
            }
        }
    }
}
