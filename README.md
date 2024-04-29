# A2 Genetic Algorithms

# Your readme should include the following information. Each student needs to submit all of this information themselves, even when pair programming. 

Programming Partner Name: n/a

Other Collaborators (and kudos to helpful members of the class):

If you have a selfie of your TA Hours Study Group, include it here!

References Used: n/a

Brief summary of what you observed about your genetic algorithm: I noticed that the initial changes between rounds are the most noticeable, and there were fewer changes round to round after more genetic selection has occurred.

Reflection on your experience with this assignment: Debugging with print statements was incredibly useful. I found that error statements gave too little information, and adding egregious amounts of print statements helped me track down issues very effectively. I tracked down a typo of '++' when I meant '--', casting Math.random() to an int too early so it always rounded to zero, and a mistake in doesMutate() through this method. Tests like assertTrue() wouldn't have been as helpful to find the source of these issues, so I'm glad I used these print statements so they saved me some time.